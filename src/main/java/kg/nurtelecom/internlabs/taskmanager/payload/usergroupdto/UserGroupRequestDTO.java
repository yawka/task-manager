package kg.nurtelecom.internlabs.taskmanager.payload.usergroupdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kg.nurtelecom.internlabs.taskmanager.model.UserGroup;
import lombok.Data;

@Data
public class UserGroupRequestDTO {

    private Long userGroupId;

    @NotBlank(message = "У группы должно быть имя")
    @NotNull(message = "У группы должно быть имя")
    private String groupName;

    private UserGroup parentGroup;
}
