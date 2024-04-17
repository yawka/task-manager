    package kg.nurtelecom.internlabs.taskmanager.mapper;

    import kg.nurtelecom.internlabs.taskmanager.payload.usergroupdto.UserGroupRequestDTO;
    import kg.nurtelecom.internlabs.taskmanager.payload.usergroupdto.UserGroupResponseDTO;
    import kg.nurtelecom.internlabs.taskmanager.model.UserGroup;
    import org.mapstruct.Mapper;
    import org.mapstruct.factory.Mappers;

    @Mapper(componentModel = "spring")
    public interface UserGroupsMapper {
        UserGroupsMapper INSTANCE = Mappers.getMapper(UserGroupsMapper.class);

        UserGroup toUserGroup(UserGroupRequestDTO userGroupRequestDTO);

        UserGroupResponseDTO toUserGroupResponse(UserGroup userGroup);
    }
