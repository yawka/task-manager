package kg.nurtelecom.internlabs.taskmanager.payload.emailVerificationRequest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class VerificationRequest {
    @NotBlank(message = "Код не может быть пуcтым")
    @NotNull(message = "Код не может быть пустым")
    private String code;
}
