package kg.nurtelecom.internlabs.taskmanager.service.impl;

import kg.nurtelecom.internlabs.taskmanager.exception.ImageSaveException;
import kg.nurtelecom.internlabs.taskmanager.exception.ProjectNotFoundException;
import kg.nurtelecom.internlabs.taskmanager.model.Project;
import kg.nurtelecom.internlabs.taskmanager.model.Task;
import kg.nurtelecom.internlabs.taskmanager.payload.taskdto.TaskResponseDTO;
import kg.nurtelecom.internlabs.taskmanager.payload.userdto.UserRequestDTO;
import kg.nurtelecom.internlabs.taskmanager.payload.userdto.UserResponseDTO;
import kg.nurtelecom.internlabs.taskmanager.exception.UserNotFoundException;
import kg.nurtelecom.internlabs.taskmanager.mapper.UserMapper;
import kg.nurtelecom.internlabs.taskmanager.model.User;
import kg.nurtelecom.internlabs.taskmanager.repository.ProjectRepository;
import kg.nurtelecom.internlabs.taskmanager.repository.UserRepository;
import kg.nurtelecom.internlabs.taskmanager.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final CloudinaryServiceImpl cloudinaryService;

    private final ProjectRepository projectRepository;

    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, CloudinaryServiceImpl cloudinaryService, ProjectRepository projectRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.cloudinaryService = cloudinaryService;
        this.projectRepository = projectRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserResponseDTO create(UserRequestDTO userRequestDTO) {
        User createdUser = userRepository.save(userMapper.toUser(userRequestDTO));
        return userMapper.toUserResponseDTO(createdUser);

    }

    @Override
    public UserResponseDTO update(Long userId, UserRequestDTO userRequestDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с таким id не найден!"));

        user.setUsername(userRequestDTO.getUsername());
        user.setPosition(userRequestDTO.getPosition());
        user.setDirectSupervisor(userRequestDTO.getDirectSupervisor());
        user.setImmediateSupervisor(userRequestDTO.getImmediateSupervisor());

        return userMapper.toUserResponseDTO(userRepository.save(user));

    }

    @Override
    public String uploadUserAvatar(User user, MultipartFile file) {
        String folderName = "user_avatars";

        User updateUser = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));

        String avatarUrl = cloudinaryService.uploadFile(file, folderName);

        if (avatarUrl == null) {
            throw new ImageSaveException("Ошибка при сохранении фото");
        }
        updateUser.setAvatarUrl(avatarUrl);
        userRepository.save(updateUser);
        return avatarUrl;
    }

    @Override
    public UserResponseDTO get(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("Пользователь с таким id не найден!"));

        return userMapper.toUserResponseDTO(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("Пользователь с таким id не найден!"));

        userRepository.deleteById(id);
    }

    @Override
    public Page<UserResponseDTO> getAllUsers(Pageable pageable) {
        Page<User> userPage = userRepository.findAll(pageable);

        return userPage.map(userMapper::toUserResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDTO> getUsersByProject(Long projectId) {
        Set<Long> userIds = getUserIdsByProject(projectId);
        return userRepository.findAllById(userIds).stream().map(userMapper::toUserResponseDTO).collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public Set<Long> getUserIdsByProject(Long projectId) {
        return projectRepository.findByProjectIdAndDeletedFalse(projectId)
                .map(project -> {
                    Set<Long> userIds = new HashSet<>(project.getUserIds());
                    userIds.add(project.getCreator().getUserId());
                    return userIds;
                })
                .orElseThrow(() -> new RuntimeException("Project with ID " + projectId + " not found or deleted"));
    }
}
