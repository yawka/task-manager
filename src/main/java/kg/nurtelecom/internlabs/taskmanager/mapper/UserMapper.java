package kg.nurtelecom.internlabs.taskmanager.mapper;

import kg.nurtelecom.internlabs.taskmanager.payload.userdto.UserRequestDTO;
import kg.nurtelecom.internlabs.taskmanager.payload.userdto.UserResponseDTO;
import kg.nurtelecom.internlabs.taskmanager.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {UserGroupsMapper.class})
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    User toUser(UserRequestDTO userRequestDTO);
    User toUser(UserResponseDTO userResponseDTO);
    UserResponseDTO toUserResponseDTO(User user);
}