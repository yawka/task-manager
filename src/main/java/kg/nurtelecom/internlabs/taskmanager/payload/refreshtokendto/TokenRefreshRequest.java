package kg.nurtelecom.internlabs.taskmanager.payload.refreshtokendto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenRefreshRequest {
    @NotBlank
    private String refreshtoken;
}
