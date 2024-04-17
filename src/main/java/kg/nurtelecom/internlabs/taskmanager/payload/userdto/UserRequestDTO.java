package kg.nurtelecom.internlabs.taskmanager.payload.userdto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import kg.nurtelecom.internlabs.taskmanager.model.User;
import lombok.Data;


@Data
public class UserRequestDTO {

    private Long userId;

    @NotBlank(message = "Имя не должно быть пустым")
    private String username;

    @NotBlank(message = "Email не должен быть пустым")
    @Email(message = "Неверный формат почты")
    private String email;

    @NotBlank(message = "Пароль не должен быть пустым")
    @Size(min = 8, message = "Пароль не должен быть меньше 8 символов")
    private String password;

    private String position;

    private User directSupervisor;

    private User immediateSupervisor;
}
