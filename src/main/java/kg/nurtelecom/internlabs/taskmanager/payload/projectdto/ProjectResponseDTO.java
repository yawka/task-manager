package kg.nurtelecom.internlabs.taskmanager.payload.projectdto;

import kg.nurtelecom.internlabs.taskmanager.model.Project;
import kg.nurtelecom.internlabs.taskmanager.model.enums.ProjectConstraints;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Data
public class ProjectResponseDTO {
    private Long projectId;

    private String projectName;

    private String description;

    private LocalDateTime createdDate;

    private Project parentProject;

    private List<ProjectConstraints> constraints;
}
