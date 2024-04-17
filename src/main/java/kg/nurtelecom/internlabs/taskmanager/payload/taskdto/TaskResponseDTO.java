package kg.nurtelecom.internlabs.taskmanager.payload.taskdto;

import kg.nurtelecom.internlabs.taskmanager.model.Task;
import kg.nurtelecom.internlabs.taskmanager.model.User;
import kg.nurtelecom.internlabs.taskmanager.payload.userdto.UserResponseDTO;
import lombok.Data;

@Data
public class TaskResponseDTO {

    private Long taskId;

    private String taskName;

    private String description;

    private String endDate;

    private String startDate;

    private Integer duration;

    private Double progress;

    private Long parentTaskId;

    private UserResponseDTO creator;

    private UserResponseDTO executor;
}
