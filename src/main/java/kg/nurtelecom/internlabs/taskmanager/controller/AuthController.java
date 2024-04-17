package kg.nurtelecom.internlabs.taskmanager.controller;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import kg.nurtelecom.internlabs.taskmanager.payload.emailVerificationRequest.VerificationRequest;
import kg.nurtelecom.internlabs.taskmanager.payload.emailVerificationRequest.CodeVerificationRequest;
import kg.nurtelecom.internlabs.taskmanager.payload.jwtresponse.JwtResponse;
import kg.nurtelecom.internlabs.taskmanager.payload.logindto.LoginRequest;
import kg.nurtelecom.internlabs.taskmanager.payload.logoutdto.LogoutRequest;
import kg.nurtelecom.internlabs.taskmanager.payload.passwordrequestdto.ResetPasswordRequestDTO;
import kg.nurtelecom.internlabs.taskmanager.payload.signupdto.SignupRequest;
import kg.nurtelecom.internlabs.taskmanager.payload.refreshtokendto.TokenRefreshRequest;
import kg.nurtelecom.internlabs.taskmanager.security.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        return new ResponseEntity<>(authService.registerUser(signUpRequest), HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse = authService.authenticateUser(loginRequest);
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequest tokenRefreshRequest) {
        return new ResponseEntity<>(authService.refreshToken(tokenRefreshRequest), HttpStatus.OK);
    }

    @PutMapping("/resend-verification-code")
    public ResponseEntity<?> resendVerificationCode(@Valid @RequestBody CodeVerificationRequest codeVerificationRequest) {
        authService.resendVerificationCode(codeVerificationRequest);
        return ResponseEntity.ok("Код подтверждения отправлен на вашу почту.");
    }

    @PostMapping("/verify-email")
    public ResponseEntity<?> verifyEmailToken(@Valid @RequestBody VerificationRequest verificationRequest) {
        boolean isVerified = authService.verifyEmail(verificationRequest);
        if (isVerified) {
            return new ResponseEntity<>("Пользователь успешно верифицирован.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Неверный код верификации или срок действия истёк.", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody CodeVerificationRequest codeVerificationRequest) {
        authService.generateResetPasswordToken(codeVerificationRequest);
        return ResponseEntity.ok("Код подтверждения отправлен на вашу почту.");
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyResetPasswordToken(@Valid @RequestBody VerificationRequest verificationRequest) {
        boolean isVerified = authService.verifyResetPasswordToken(verificationRequest);
        if (isVerified) {
            return new ResponseEntity<>("Подтверждение почты прошло усешно.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Неверный код верификации или срок действия истёк.", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody ResetPasswordRequestDTO resetPasswordRequestDTO) {
        authService.resetPassword(resetPasswordRequestDTO);
        return ResponseEntity.ok("Смена пароля прошла успешна");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@NotNull @RequestBody LogoutRequest logoutRequest) {
        authService.deleteRefreshToken(logoutRequest.getUserId());
        return ResponseEntity.ok("Сеанс завершен");
    }
}

