package kg.nurtelecom.internlabs.taskmanager.service.impl;

import jakarta.transaction.Transactional;
import kg.nurtelecom.internlabs.taskmanager.mapper.UserMapper;
import kg.nurtelecom.internlabs.taskmanager.payload.projectdto.ProjectRequestDTO;
import kg.nurtelecom.internlabs.taskmanager.payload.projectdto.ProjectResponseDTO;
import kg.nurtelecom.internlabs.taskmanager.exception.InvalidProjectOperationException;
import kg.nurtelecom.internlabs.taskmanager.exception.ProjectNotFoundException;
import kg.nurtelecom.internlabs.taskmanager.exception.UserNotFoundException;
import kg.nurtelecom.internlabs.taskmanager.mapper.ProjectsMapper;
import kg.nurtelecom.internlabs.taskmanager.model.Project;
import kg.nurtelecom.internlabs.taskmanager.model.User;
import kg.nurtelecom.internlabs.taskmanager.payload.userdto.UserResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import kg.nurtelecom.internlabs.taskmanager.repository.ProjectRepository;
import kg.nurtelecom.internlabs.taskmanager.repository.TaskRepository;
import kg.nurtelecom.internlabs.taskmanager.repository.UserRepository;
import kg.nurtelecom.internlabs.taskmanager.service.ProjectService;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    private final TaskRepository taskRepository;

    private final ProjectsMapper projectsMapper;
    private final UserMapper userMapper;

    public ProjectServiceImpl(ProjectRepository projectRepository, UserRepository userRepository, TaskRepository taskRepository, ProjectsMapper projectsMapper, UserMapper userMapper) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.projectsMapper = projectsMapper;
        this.userMapper = userMapper;
    }

    /**
     * Creates a new project based on the provided input DTO.
     *
     * @param projectRequestDto the input DTO containing information for the new project.
     * @return a {@link ProjectResponseDTO} representing the newly created project.
     * @throws ProjectNotFoundException if the specified parent project is not found.
     */

    @Override
    public ProjectResponseDTO create(User user, ProjectRequestDTO projectRequestDto) {
        Project project = projectsMapper.toProject(projectRequestDto);
        project.setCreator(user);
        Project createdProject = projectRepository.save(project);
        return projectsMapper.projectToProjectResponseDTO(createdProject);
    }

    /**
     * Updates the specified project with the information provided in the input DTO.
     *
     * @param projectId       the identifier of the project to be updated.
     * @param projectRequestDto the input DTO containing the updated project information.
     * @return a {@link ProjectResponseDTO} representing the updated project.
     * @throws ProjectNotFoundException      if the project with the provided identifier is not found.
     * @throws InvalidProjectOperationException if an attempt is made to set the project itself as the parent
     *                                         or create a recursive relationship.
     */
    @Override
    public ProjectResponseDTO update(Long projectId, ProjectRequestDTO projectRequestDto) {
        Project project = projectRepository.findByProjectIdAndDeletedFalse(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Проект не найден"));
        project.setProjectName(projectRequestDto.getProjectName());
        project.setDescription(projectRequestDto.getDescription());
        project.setParentProject(projectRequestDto.getParentProject());
        project.setCreatedDate(projectRequestDto.getCreatedDate());
        project.setUserIds(projectRequestDto.getUserIds());
        project.setConstraints(projectRequestDto.getConstraints());
        return projectsMapper.projectToProjectResponseDTO(projectRepository.save(project));
    }

    /**
     * Retrieves the project with the specified identifier.
     *
     * @param projectId the identifier of the project to be retrieved.
     * @return a {@link ProjectResponseDTO} representing the retrieved project.
     * @throws ProjectNotFoundException if the project with the provided identifier is not found.
     */
    @Override
    public ProjectResponseDTO get(Long projectId) {
        Project project = projectRepository.findByProjectIdAndDeletedFalse(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Проект не найден"));
        return projectsMapper.projectToProjectResponseDTO(project);
    }


    /**
     * Retrieves a paginated list of projects.
     *
     * @return a list of {@link ProjectResponseDTO} representing the projects on the specified page.
     */
    @Override
    public Page<ProjectResponseDTO> getAllProjects(Pageable pageable) {
        Page<Project> projectPage = projectRepository.findAllByDeletedFalse(pageable);
        return projectPage.map(projectsMapper::projectToProjectResponseDTO);
    }

    @Override
    public Page<ProjectResponseDTO> getAllProjects(User user, Pageable pageable) {
        Page<Project> projects = projectRepository.findByUserIdsOrCreator(user.getUserId(), pageable);
        return projects.map(projectsMapper::projectToProjectResponseDTO);
    }

    @Override
    public Page<UserResponseDTO> getAllUsersByProjectId(Long projectId, Pageable pageable) {
        return projectRepository.findUserIdsAndCreatorByProjectId(projectId, pageable);
    }

    @Override
    @Transactional
    public void delete(Long projectId) {
        Project project = projectRepository.findByProjectIdAndDeletedFalse(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Проект с таким id не найден"));
        markAsDeleted(project);
        markProjectTasksAsDeleted(project);
    }

    @Override
    public void addUser(Long projectId, Long userId, Pageable pageable) {
        Project project = projectRepository.findByProjectIdAndDeletedFalse(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Проект не найден"));
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с таким id не найден"));
        project.getUserIds().add(userId);
        projectRepository.save(project);
    }

    private void markAsDeleted(Project project) {
        project.setDeleted(true);
        projectRepository.save(project);


        List<Project> childProjects = projectRepository.findByParentProject(project);
        for (Project childProject : childProjects) {
            markAsDeleted(childProject);
        }
    }

    private void markProjectTasksAsDeleted(Project project) {
        taskRepository.markAllTasksByProjectAsDeleted(project);
    }
}
