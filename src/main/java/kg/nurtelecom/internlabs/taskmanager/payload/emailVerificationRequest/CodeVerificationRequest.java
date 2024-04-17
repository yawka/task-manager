package kg.nurtelecom.internlabs.taskmanager.payload.emailVerificationRequest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;


@Getter
public class CodeVerificationRequest {
    @NotBlank
    @NotNull
    private String email;
}
