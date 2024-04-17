package kg.nurtelecom.internlabs.taskmanager.payload.projectdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kg.nurtelecom.internlabs.taskmanager.model.Project;
import kg.nurtelecom.internlabs.taskmanager.model.enums.ProjectConstraints;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
public class ProjectRequestDTO {

    private Long projectId;

    @NotNull
    @NotBlank(message = "Имя проекта не должно быть пустым")
    private String projectName;

    private String description;

    private LocalDateTime createdDate;

    private Project parentProject;

    private Set<Long> userIds;

    private List<ProjectConstraints> constraints;
}
