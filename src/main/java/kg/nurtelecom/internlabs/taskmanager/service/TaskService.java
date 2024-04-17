package kg.nurtelecom.internlabs.taskmanager.service;

import kg.nurtelecom.internlabs.taskmanager.model.LocalDateRange;
import kg.nurtelecom.internlabs.taskmanager.model.Project;
import kg.nurtelecom.internlabs.taskmanager.payload.taskdto.TaskResponseDTO;
import kg.nurtelecom.internlabs.taskmanager.payload.taskdto.TaskRequestDTO;
import kg.nurtelecom.internlabs.taskmanager.model.User;
import kg.nurtelecom.internlabs.taskmanager.payload.userdto.UserResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public interface TaskService {
    TaskResponseDTO create(TaskRequestDTO taskRequestDto, User user);
    TaskResponseDTO update(Long taskId, TaskRequestDTO taskRequestDto);
    TaskResponseDTO get(Long taskId);
    Page<TaskResponseDTO> getTasks(Long projectId, Pageable pageable);
    Page<TaskResponseDTO> getTasks(Long projectId, User user, Pageable pageable);
    Page<TaskResponseDTO> getUserAllTasks(User user, Pageable pageable);
    List<LocalDateRange> getUniqueTaskDatesForProject(Long projectId);
    void delete(Long taskId);
}

