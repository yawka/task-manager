package kg.nurtelecom.internlabs.taskmanager.service;

import kg.nurtelecom.internlabs.taskmanager.payload.projectdto.ProjectResponseDTO;
import kg.nurtelecom.internlabs.taskmanager.payload.projectdto.ProjectRequestDTO;
import kg.nurtelecom.internlabs.taskmanager.model.User;
import kg.nurtelecom.internlabs.taskmanager.payload.userdto.UserResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface ProjectService {
    ProjectResponseDTO create(User user, ProjectRequestDTO projectRequestDto);
    ProjectResponseDTO update(Long projectId, ProjectRequestDTO projectRequestDto);
    ProjectResponseDTO get(Long projectId);
    Page<ProjectResponseDTO> getAllProjects(Pageable pageable);
    Page<ProjectResponseDTO> getAllProjects(User user, Pageable pageable);
    Page<UserResponseDTO> getAllUsersByProjectId(Long projectId, Pageable pageable);
    void delete(Long projectId);

    void addUser(Long projectId, Long userId, Pageable pageable);
}
