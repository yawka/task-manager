package kg.nurtelecom.internlabs.taskmanager.payload.passwordrequestdto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ResetPasswordRequestDTO {
    @NotBlank(message = "Почта не может быть пустой")
    @Email
    private String email;

    @NotBlank(message = "пароль не может быть пустым")
    private String password;
}
