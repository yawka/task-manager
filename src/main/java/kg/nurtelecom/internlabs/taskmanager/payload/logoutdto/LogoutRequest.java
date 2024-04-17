package kg.nurtelecom.internlabs.taskmanager.payload.logoutdto;

import lombok.Getter;

import jakarta.validation.constraints.NotBlank;

@Getter
public class LogoutRequest {
    @NotBlank(message = "UserId couldn't be null")
    private Long userId;
}
