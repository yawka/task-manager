package kg.nurtelecom.internlabs.taskmanager.mapper;

import javax.annotation.processing.Generated;
import kg.nurtelecom.internlabs.taskmanager.model.User;
import kg.nurtelecom.internlabs.taskmanager.payload.userdto.UserRequestDTO;
import kg.nurtelecom.internlabs.taskmanager.payload.userdto.UserResponseDTO;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-01T15:57:14+0600",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toUser(UserRequestDTO userRequestDTO) {
        if ( userRequestDTO == null ) {
            return null;
        }

        User user = new User();

        user.setUserId( userRequestDTO.getUserId() );
        user.setUsername( userRequestDTO.getUsername() );
        user.setEmail( userRequestDTO.getEmail() );
        user.setPosition( userRequestDTO.getPosition() );
        user.setPassword( userRequestDTO.getPassword() );
        user.setDirectSupervisor( userRequestDTO.getDirectSupervisor() );
        user.setImmediateSupervisor( userRequestDTO.getImmediateSupervisor() );

        return user;
    }

    @Override
    public User toUser(UserResponseDTO userResponseDTO) {
        if ( userResponseDTO == null ) {
            return null;
        }

        User user = new User();

        user.setUserId( userResponseDTO.getUserId() );
        user.setUsername( userResponseDTO.getUsername() );
        user.setEmail( userResponseDTO.getEmail() );
        user.setPosition( userResponseDTO.getPosition() );
        user.setDirectSupervisor( userResponseDTO.getDirectSupervisor() );
        user.setImmediateSupervisor( userResponseDTO.getImmediateSupervisor() );

        return user;
    }

    @Override
    public UserResponseDTO toUserResponseDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponseDTO userResponseDTO = new UserResponseDTO();

        userResponseDTO.setUserId( user.getUserId() );
        userResponseDTO.setUsername( user.getUsername() );
        userResponseDTO.setEmail( user.getEmail() );
        userResponseDTO.setPosition( user.getPosition() );
        userResponseDTO.setDirectSupervisor( user.getDirectSupervisor() );
        userResponseDTO.setImmediateSupervisor( user.getImmediateSupervisor() );

        return userResponseDTO;
    }
}
