package kg.nurtelecom.internlabs.taskmanager.payload.usergroupdto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import kg.nurtelecom.internlabs.taskmanager.model.UserGroup;
import lombok.Data;

@Data
public class UserGroupResponseDTO {
    private Long userGroupId;
    private String groupName;
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private UserGroup parentGroup;
}
