package kg.nurtelecom.internlabs.taskmanager.service.impl;

import kg.nurtelecom.internlabs.taskmanager.exception.ProjectConstraintsException;
import kg.nurtelecom.internlabs.taskmanager.exception.ProjectNotFoundException;
import kg.nurtelecom.internlabs.taskmanager.exception.TaskNotFoundException;
import kg.nurtelecom.internlabs.taskmanager.mapper.TaskMapper;
import kg.nurtelecom.internlabs.taskmanager.mapper.UserMapper;
import kg.nurtelecom.internlabs.taskmanager.model.LocalDateRange;
import kg.nurtelecom.internlabs.taskmanager.model.Project;
import kg.nurtelecom.internlabs.taskmanager.model.enums.ProjectConstraints;
import kg.nurtelecom.internlabs.taskmanager.model.Task;
import kg.nurtelecom.internlabs.taskmanager.model.User;
import kg.nurtelecom.internlabs.taskmanager.notification.observer.Publisher;
import kg.nurtelecom.internlabs.taskmanager.payload.taskdto.TaskRequestDTO;
import kg.nurtelecom.internlabs.taskmanager.payload.taskdto.TaskResponseDTO;
import kg.nurtelecom.internlabs.taskmanager.payload.userdto.UserResponseDTO;
import kg.nurtelecom.internlabs.taskmanager.repository.ProjectRepository;
import kg.nurtelecom.internlabs.taskmanager.repository.TaskRepository;
import kg.nurtelecom.internlabs.taskmanager.repository.UserRepository;
import kg.nurtelecom.internlabs.taskmanager.service.TaskService;
import kg.nurtelecom.internlabs.taskmanager.service.validation.ConstraintContext;
import kg.nurtelecom.internlabs.taskmanager.service.validation.strategies.NoOverlapConstraint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ConstraintContext constraintContext;
    private final TaskMapper taskMapper;

    private final UserMapper userMapper;
    private final Publisher publisher;

    public TaskServiceImpl(TaskRepository taskRepository, ProjectRepository projectRepository, UserRepository userRepository, ConstraintContext constraintContext, TaskMapper taskMapper, UserMapper userMapper, Publisher publisher) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.constraintContext = constraintContext;
        this.taskMapper = taskMapper;
        this.userMapper = userMapper;
        this.publisher = publisher;
    }

    @Override
    public TaskResponseDTO create(TaskRequestDTO taskRequestDto, User user) {
        if (taskRequestDto.getProject().getConstraints() != null) {
            checkProjectConstrains(taskRequestDto, taskRequestDto.getProject());
        }
        Task task = taskMapper.toTask(taskRequestDto);
        task.setCreator(user);
        taskRepository.save(task);
        return taskMapper.toTaskResponseDTO(task);
    }

    public void checkProjectConstrains(TaskRequestDTO taskRequestDto, Project project) {
        for (ProjectConstraints constraint : project.getConstraints()) {
            switch (constraint.name()) {
                case "NO_OVERLAP" -> constraintContext.setStrategy(new NoOverlapConstraint(taskRepository));
            }
            if (!constraintContext.validate(taskRequestDto)) {
                throw new ProjectConstraintsException("Task validation failed for constraint: " + constraint.name());
            }
        }
    }

    @Override
    public TaskResponseDTO update(Long taskId, TaskRequestDTO taskRequestDto) {
        Task existingTask = taskRepository.findTaskByTaskIdAndIsDeletedFalse(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Задача не найдена"));

        existingTask.setTaskName(taskRequestDto.getTaskName());
        existingTask.setDescription(taskRequestDto.getDescription());
        existingTask.setStartDate(taskRequestDto.getStartDate());
        existingTask.setEndDate(taskRequestDto.getEndDate());
        existingTask.setDuration(taskRequestDto.getDuration());
        existingTask.setProgress(taskRequestDto.getProgress());
        existingTask.setExecutor(userMapper.toUser(taskRequestDto.getExecutor()));

        Task updatedTask = taskRepository.save(existingTask);

        return taskMapper.toTaskResponseDTO(updatedTask);
    }

    @Override
    public TaskResponseDTO get(Long taskId) {

        Task task = taskRepository.findTaskByTaskIdAndIsDeletedFalse(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Задача не найдена"));

        return taskMapper.toTaskResponseDTO(task);
    }

    @Override
    public Page<TaskResponseDTO> getTasks(Long projectId, Pageable pageable) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Проект не найден"));
        Page<Task> tasks = taskRepository.findByProjectAndIsDeletedFalse(project, pageable);
        System.out.println(tasks.stream().toList());
        return tasks.map(taskMapper::toTaskResponseDTO);
    }

    @Override
    public Page<TaskResponseDTO> getTasks(Long projectId, User user, Pageable pageable) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Проект не найден"));
        Page<Task> tasks = taskRepository.findTasksByProjectAndUserAndNotDeleted(project, user, pageable);
        return tasks.map(taskMapper::toTaskResponseDTO);
    }
    @Override
    public Page<TaskResponseDTO> getUserAllTasks(User user, Pageable pageable) {
        Page<Task> tasks = taskRepository.findAllByCreatorOrExecutor(user, pageable);
        return tasks.map(taskMapper::toTaskResponseDTO);
    }

    public List<LocalDateRange> getUniqueTaskDatesForProject(Long projectId) {
        List<Task> tasks = taskRepository.findByProjectIdAndIsDeletedFalse(projectId);

        List<LocalDateRange> dates = new ArrayList<>();

        for (Task task : tasks) {
            LocalDateRange datePairs = new LocalDateRange(task.getStartDate().toLocalDate(), task.getEndDate().toLocalDate());
            dates.add(datePairs);
        }

        return dates;
    }


    @Override
    public void delete(Long taskId) {
        if (!(taskRepository.existsById(taskId))) {
            throw new TaskNotFoundException("Задача с таким id не существует");
        }
        long childTaskCount = taskRepository.countByParentTaskTaskId(taskId);

        if (childTaskCount > 0) {
            taskRepository.markSubTasksAsDeleted(taskId);
        }
        taskRepository.markTaskAsDeleted(taskId);
    }
}
