package kg.nurtelecom.internlabs.taskmanager.payload.taskdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kg.nurtelecom.internlabs.taskmanager.model.Project;
import kg.nurtelecom.internlabs.taskmanager.model.Task;
import kg.nurtelecom.internlabs.taskmanager.payload.userdto.UserResponseDTO;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class TaskRequestDTO {

    private Long taskId;

    @NotBlank(message = "Имя задачи не должно быть пустым или содержать только пробелы")
    private String taskName;

    private String description;

    @NotNull(message = "Дата начала не может быть пустой")
    private LocalDateTime startDate;

    @NotNull(message = "Дата окончания не может быть пустой")
    private LocalDateTime endDate;

    @NotNull(message = "Длительность не может быть пустой")
    private Integer duration;

    @NotNull(message = "Прогресс не может быть пустым")
    private Double progress;

    private Task parentTask;

    @NotNull(message = "Проект не может быть пустым")
    private Project project;

    private UserResponseDTO executor;
}
