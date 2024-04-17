package kg.nurtelecom.internlabs.taskmanager.controller;

import jakarta.validation.Valid;
import kg.nurtelecom.internlabs.taskmanager.model.LocalDateRange;
import kg.nurtelecom.internlabs.taskmanager.model.User;
import kg.nurtelecom.internlabs.taskmanager.payload.taskdto.TaskRequestDTO;
import kg.nurtelecom.internlabs.taskmanager.payload.taskdto.TaskResponseDTO;
import kg.nurtelecom.internlabs.taskmanager.service.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;


@RestController
@RequestMapping("/tasks")
@CrossOrigin(origins = {"http://localhost:8081", "http://localhost:8816"})
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PreAuthorize("hasRole('DEVELOPER') or hasRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<TaskResponseDTO> createTask(@AuthenticationPrincipal User user,
                                                      @RequestBody @Valid TaskRequestDTO taskRequestDto) {
        TaskResponseDTO createdTask = taskService.create(taskRequestDto, user);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('DEVELOPER') or hasRole('ADMIN')")
    @PutMapping("/{taskId}")
    public ResponseEntity<TaskResponseDTO> updateTask(@AuthenticationPrincipal User user,
                                                      @PathVariable Long taskId, @RequestBody @Valid TaskRequestDTO taskRequestDto) {
        TaskResponseDTO updatedTask = taskService.update(taskId, taskRequestDto);
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') or hasRole('DEVELOPER') or hasRole('ADMIN')")
    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponseDTO> getTask(@PathVariable Long taskId) {
        TaskResponseDTO taskResponseDto = taskService.get(taskId);
        return new ResponseEntity<>(taskResponseDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') or hasRole('DEVELOPER') or hasRole('ADMIN')")
    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<TaskResponseDTO>> getTasks(@PathVariable Long projectId,
                                                          @PageableDefault(page = 0, size = 10, sort = "taskId", direction = Sort.Direction.ASC)
                                                          Pageable pageable) {
        Page<TaskResponseDTO> tasks = taskService.getTasks(projectId, pageable);
        List<TaskResponseDTO> taskResponseDTOS = tasks.getContent();
        return new ResponseEntity<>(taskResponseDTOS, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('DEVELOPER') or hasRole('ADMIN')")
    @DeleteMapping("/{taskId}")
    public ResponseEntity<TaskResponseDTO> deleteTask(@PathVariable Long taskId) {
        taskService.delete(taskId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('DEVELOPER') or hasRole('ADMIN')")
    @GetMapping("/{projectId}/dates")
    public ResponseEntity<List<LocalDateRange>> getUniqueDates(@PathVariable Long projectId) {
        List<LocalDateRange> dates = taskService.getUniqueTaskDatesForProject(projectId);
        return new ResponseEntity<>(dates, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') or hasRole('DEVELOPER') or hasRole('ADMIN')")
    @GetMapping("/user")
    public ResponseEntity<List<TaskResponseDTO>> getUserTasks(@AuthenticationPrincipal User user, @PageableDefault(page = 0, size = 10, sort = "taskId", direction = Sort.Direction.ASC)
    Pageable pageable) {
        Page<TaskResponseDTO> tasksPage = taskService.getUserAllTasks(user, pageable);
        List<TaskResponseDTO> taskResponseDTOList = tasksPage.getContent();
        return new ResponseEntity<>(taskResponseDTOList, HttpStatus.OK);
    }
}

