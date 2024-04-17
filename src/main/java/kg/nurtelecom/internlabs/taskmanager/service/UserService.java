package kg.nurtelecom.internlabs.taskmanager.service;

import kg.nurtelecom.internlabs.taskmanager.model.User;
import kg.nurtelecom.internlabs.taskmanager.payload.userdto.UserResponseDTO;
import kg.nurtelecom.internlabs.taskmanager.payload.userdto.UserRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface UserService {

    UserResponseDTO create(UserRequestDTO userRequestDTO);

    UserResponseDTO update(Long id, UserRequestDTO userRequestDTO);

    String uploadUserAvatar(User user, MultipartFile file);

    UserResponseDTO get(Long id);
    Page<UserResponseDTO> getAllUsers(Pageable pageable);

    @Transactional(readOnly = true)
    List<UserResponseDTO> getUsersByProject(Long projectId);
    void delete(Long id);
}
