package kg.nurtelecom.internlabs.taskmanager.mapper;

import javax.annotation.processing.Generated;
import kg.nurtelecom.internlabs.taskmanager.model.UserGroup;
import kg.nurtelecom.internlabs.taskmanager.payload.usergroupdto.UserGroupRequestDTO;
import kg.nurtelecom.internlabs.taskmanager.payload.usergroupdto.UserGroupResponseDTO;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-26T16:15:55+0600",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class UserGroupsMapperImpl implements UserGroupsMapper {

    @Override
    public UserGroup toUserGroup(UserGroupRequestDTO userGroupRequestDTO) {
        if ( userGroupRequestDTO == null ) {
            return null;
        }

        UserGroup userGroup = new UserGroup();

        userGroup.setUserGroupId( userGroupRequestDTO.getUserGroupId() );
        userGroup.setGroupName( userGroupRequestDTO.getGroupName() );
        userGroup.setParentGroup( userGroupRequestDTO.getParentGroup() );

        return userGroup;
    }

    @Override
    public UserGroupResponseDTO toUserGroupResponse(UserGroup userGroup) {
        if ( userGroup == null ) {
            return null;
        }

        UserGroupResponseDTO userGroupResponseDTO = new UserGroupResponseDTO();

        userGroupResponseDTO.setUserGroupId( userGroup.getUserGroupId() );
        userGroupResponseDTO.setGroupName( userGroup.getGroupName() );
        userGroupResponseDTO.setParentGroup( userGroup.getParentGroup() );

        return userGroupResponseDTO;
    }
}
