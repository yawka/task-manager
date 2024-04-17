package kg.nurtelecom.internlabs.taskmanager.security.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import kg.nurtelecom.internlabs.taskmanager.exception.*;
import kg.nurtelecom.internlabs.taskmanager.mapper.UserMapper;
import kg.nurtelecom.internlabs.taskmanager.model.*;
import kg.nurtelecom.internlabs.taskmanager.model.enums.Roles;
import kg.nurtelecom.internlabs.taskmanager.payload.emailVerificationRequest.CodeVerificationRequest;
import kg.nurtelecom.internlabs.taskmanager.payload.emailVerificationRequest.VerificationRequest;
import kg.nurtelecom.internlabs.taskmanager.payload.jwtresponse.JwtResponse;
import kg.nurtelecom.internlabs.taskmanager.payload.logindto.LoginRequest;
import kg.nurtelecom.internlabs.taskmanager.payload.passwordrequestdto.ResetPasswordRequestDTO;
import kg.nurtelecom.internlabs.taskmanager.payload.signupdto.SignupRequest;
import kg.nurtelecom.internlabs.taskmanager.payload.refreshtokendto.TokenRefreshRequest;
import kg.nurtelecom.internlabs.taskmanager.payload.refreshtokendto.TokenRefreshResponse;
import kg.nurtelecom.internlabs.taskmanager.payload.userdto.UserResponseDTO;
import kg.nurtelecom.internlabs.taskmanager.repository.RoleRepository;
import kg.nurtelecom.internlabs.taskmanager.repository.UserRepository;
import kg.nurtelecom.internlabs.taskmanager.repository.VerificationTokenRepository;
import kg.nurtelecom.internlabs.taskmanager.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    RoleRepository roleRepository;

    private final UserMapper userMapper;

    private final JavaMailSenderImpl mailSender;

    @Autowired
    AuthenticationManager authenticationManager;

    private final RefreshTokenService refreshTokenService;

    private final VerificationTokenRepository verificationTokenRepository;

    private final UserRepository userRepository;

    public AuthService(UserMapper userMapper, JavaMailSenderImpl mailSender, RefreshTokenService refreshTokenService, VerificationTokenRepository verificationTokenRepository, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.mailSender = mailSender;
        this.refreshTokenService = refreshTokenService;
        this.verificationTokenRepository = verificationTokenRepository;
        this.userRepository = userRepository;
    }

    public JwtResponse authenticateUser(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = (User) authentication.getPrincipal();

        String jwt = jwtUtils.generateJwtToken(authentication);

        List<String> roles = user.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getUserId());


        return new JwtResponse(jwt,
                refreshToken.getToken(),
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                roles);
    }

    public TokenRefreshResponse refreshToken(TokenRefreshRequest tokenRefreshRequest) {

        String requestRefreshToken = tokenRefreshRequest.getRefreshtoken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getUsername());
                    return new TokenRefreshResponse(token, requestRefreshToken);
                })
                .orElseThrow(() -> new RefreshTokenNotFoundException(requestRefreshToken,
                        "Токен не найден"));
    }


    public UserResponseDTO registerUser(SignupRequest signUpRequest) {

        User user = userRepository.findByUsernameOrEmail(signUpRequest.getUsername(), signUpRequest.getEmail());

        if (user != null && !user.isEnabled()) {
            throw new EmailVerificationException("Такой пользователь уже существует, подтвердите вашу почту");
        }

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new UsernameNotFoundException("Такое имя пользователя уже существует");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new EmailNotFoundException("Такая почта уже существует");
        }


        user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(Roles.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin" -> {
                        Role adminRole = roleRepository.findByName(Roles.ROLE_ADMIN)
                                .orElseThrow(() -> new RoleNotFoundException("Роль не найдена"));
                        roles.add(adminRole);
                    }
                    case "developer" -> {
                        Role modRole = roleRepository.findByName(Roles.ROLE_DEVELOPER)
                                .orElseThrow(() -> new RoleNotFoundException("Роль не найдена"));
                        roles.add(modRole);
                    }
                    default -> {
                        Role userRole = roleRepository.findByName(Roles.ROLE_USER)
                                .orElseThrow(() -> new RoleNotFoundException("Роль не найдена"));
                        roles.add(userRole);
                    }
                }
            });
        }

        user.setRoles(roles);
        user.setEnabled(false);
        userRepository.save(user);

        String emailCode = generateVerificationCode(user).getCode();

        try {
            sendVerificationEmailCode(user, emailCode);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        return userMapper.toUserResponseDTO(user);
    }

    public VerificationToken generateVerificationCode(User user) {
        Integer verificationCode = (int) (Math.random() * (999999 - 100000 + 1)) + 100000;
        VerificationToken verificationToken = verificationTokenRepository.findByUser(user);
        if (verificationToken == null) {
            verificationToken = new VerificationToken();
            verificationToken.setUser(user);
        }
        verificationToken.setCode(String.valueOf(verificationCode));
        verificationToken.setExpiryDate(Date.from(Instant.now().plusSeconds(3600)));

        return verificationTokenRepository.save(verificationToken);
    }


    private void sendVerificationEmailCode(User user, String emailCode) throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = "timuribragimov668@gmail.com";
        String senderName = "Nex Ops";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Your verification code is: <b>[[code]]</b><br>"
                + "Please enter this code on our website to verify your email address.<br>"
                + "Thank you,<br>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getUsername());
        content = content.replace("[[code]]",emailCode);

        helper.setText(content, true);

        mailSender.send(message);
    }

    public void resendVerificationCode(CodeVerificationRequest emailVerificationRequest) {
        User user = userRepository.findByEmailIgnoreCase(emailVerificationRequest.getEmail())
                .orElseThrow(() -> new UserNotFoundException("Пользователь с таким email не найден"));

        String emailCode = generateVerificationCode(user).getCode();

        try {
            sendVerificationEmailCode(user, emailCode);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean verifyEmail(VerificationRequest verificationRequest) {
        VerificationToken verificationToken = verificationTokenRepository.getByCode(verificationRequest.getCode());

        if (verificationToken == null || verificationToken.getUser().isEnabled()) {
            return false;
        } else {

            if (new Date().after(verificationToken.getExpiryDate())) {
                return false;
            }

            User user = userRepository.getById(verificationToken.getUser().getUserId());
            user.setEnabled(true);
            userRepository.save(user);

            verificationTokenRepository.delete(verificationToken);

            return true;
        }
    }

    public void generateResetPasswordToken(CodeVerificationRequest codeVerificationRequest) {
        User user = userRepository.findByEmailIgnoreCase(codeVerificationRequest.getEmail())
                .orElseThrow(() -> new UserNotFoundException("Пользователь с такой почтой не найден"));
        try {
            sendResetPasswordVerificationCode(generateVerificationCode(user));
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendResetPasswordVerificationCode(VerificationToken verificationToken) throws MessagingException, UnsupportedEncodingException {
        String toAddress = verificationToken.getUser().getEmail();
        String fromAddress = "timuribragimov668@gmail.com";
        String senderName = "Nex Ops";
        String subject = "Please confirm your email";
        String content = "Dear [[name]],<br>"
                + "Your verification code is: <b>[[code]]</b><br>"
                + "Please enter this code on our website to confirm your email address.<br>"
                + "Thank you,<br>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", verificationToken.getUser().getUsername());
        content = content.replace("[[code]]", verificationToken.getCode());

        helper.setText(content, true);

        mailSender.send(message);
    }


    public void resetPassword(ResetPasswordRequestDTO resetPasswordRequestTokenDTO) {
        User user = userRepository.findByEmailIgnoreCase(resetPasswordRequestTokenDTO.getEmail())
                .orElseThrow(() -> new UserNotFoundException("Пользователь с таким email не найден"));

        user.setPassword(encoder.encode(resetPasswordRequestTokenDTO.getPassword()));
        userRepository.save(user);
    }

    public boolean verifyResetPasswordToken(VerificationRequest verificationRequest) {
        VerificationToken verificationToken = verificationTokenRepository.getByCode(verificationRequest.getCode());
        if (verificationToken == null ) {
            return false;
        }
        if (new Date().after(verificationToken.getExpiryDate())) {
            return false;
        }
        verificationTokenRepository.delete(verificationToken);
        return true;
    }

    public void deleteRefreshToken(Long userId) {
        refreshTokenService.deleteRefreshToken(userId);
    }
}

