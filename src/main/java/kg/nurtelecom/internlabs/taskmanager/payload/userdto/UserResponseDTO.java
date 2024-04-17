package kg.nurtelecom.internlabs.taskmanager.payload.userdto;

import kg.nurtelecom.internlabs.taskmanager.model.User;
import lombok.Data;

@Data
public class UserResponseDTO {
    private Long userId;

    private String username;

    private String email;

    private String position;

    private User directSupervisor;

    private User immediateSupervisor;
}
