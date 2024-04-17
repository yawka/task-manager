package kg.nurtelecom.internlabs.taskmanager.controller;

import jakarta.validation.Valid;
import kg.nurtelecom.internlabs.taskmanager.payload.projectdto.ProjectRequestDTO;
import kg.nurtelecom.internlabs.taskmanager.payload.projectdto.ProjectResponseDTO;
import kg.nurtelecom.internlabs.taskmanager.exception.ProjectNotFoundException;
import kg.nurtelecom.internlabs.taskmanager.model.User;
import kg.nurtelecom.internlabs.taskmanager.payload.userdto.UserResponseDTO;
import kg.nurtelecom.internlabs.taskmanager.service.ProjectService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/projects")
@CrossOrigin(origins = {"http://localhost:8081", "http://localhost:8816"})
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PreAuthorize("hasRole('DEVELOPER') or hasRole('ADMIN')")
    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectResponseDTO> getProject(@PathVariable Long projectId) {
        ProjectResponseDTO projectResponseDto = projectService.get(projectId);
        return new ResponseEntity<>(projectResponseDto, HttpStatus.OK);
    }


    @PreAuthorize("hasRole('DEVELOPER') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ProjectResponseDTO> createProject(@AuthenticationPrincipal User user,
                                                            @RequestBody @Valid ProjectRequestDTO projectRequestDto) {
        ProjectResponseDTO createdProject = projectService.create(user, projectRequestDto);
        return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('DEVELOPER') or hasRole('ADMIN')")
    @PutMapping("/{projectId}")
    public ResponseEntity<ProjectResponseDTO> updateProject(@AuthenticationPrincipal User user,
                                                            @PathVariable Long projectId,
                                                            @RequestBody @Valid ProjectRequestDTO projectRequestDto) {
        ProjectResponseDTO updatedProject = projectService.update(projectId, projectRequestDto);
        return new ResponseEntity<>(updatedProject, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') or hasRole('DEVELOPER') or hasRole('ADMIN')")
    @DeleteMapping("/{projectId}")
    public ResponseEntity<ProjectResponseDTO> deleteProject(@PathVariable Long projectId) {
        projectService.delete(projectId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') or hasRole('DEVELOPER') or hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<ProjectResponseDTO>> getProjects(@PageableDefault(page = 0, size = 10, sort = "projectId", direction = Sort.Direction.ASC)
                                                                Pageable pageable) {
        Page<ProjectResponseDTO> projectsPage = projectService.getAllProjects(pageable);
        List<ProjectResponseDTO> projectResponseDTOList = projectsPage.getContent(); //временно
        return new ResponseEntity<>(projectResponseDTOList, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') or hasRole('DEVELOPER') or hasRole('ADMIN')")
    @GetMapping("/user")
    public ResponseEntity<List<ProjectResponseDTO>> getProjects(@AuthenticationPrincipal User user, @PageableDefault(page = 0, size = 10, sort = "projectId", direction = Sort.Direction.ASC)
                                                                Pageable pageable) {
        Page<ProjectResponseDTO> projectsPage = projectService.getAllProjects(user, pageable);
        List<ProjectResponseDTO> projectResponseDTOList = projectsPage.getContent();
        return new ResponseEntity<>(projectResponseDTOList, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') or hasRole('DEVELOPER') or hasRole('ADMIN')")
    @GetMapping("/{projectId}/users")
    public ResponseEntity<List<UserResponseDTO>> getUsersByProjectId(@AuthenticationPrincipal User user,
                                                                     @PathVariable Long projectId,
                                                                     @PageableDefault(page = 0, size = 10, sort = "projectId", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<UserResponseDTO> userPage = projectService.getAllUsersByProjectId(projectId, pageable);
        List<UserResponseDTO> users = userPage.getContent();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('DEVELOPER') or hasRole('ADMIN')")
    @PostMapping("/users")
    public ResponseEntity<?> addUserToProject(@RequestParam Long projectId,
                                                                  @RequestParam Long userId,
                                                                  @PageableDefault(page = 0, size = 10, sort = "projectId", direction = Sort.Direction.ASC) Pageable pageable) {
        projectService.addUser(projectId, userId, pageable);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

