package kg.nurtelecom.internlabs.taskmanager.service;

import kg.nurtelecom.internlabs.taskmanager.model.User;
import kg.nurtelecom.internlabs.taskmanager.payload.usergroupdto.UserGroupResponseDTO;
import kg.nurtelecom.internlabs.taskmanager.payload.usergroupdto.UserGroupRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface UserGroupService {
    Page<UserGroupResponseDTO> getUserGroups(Pageable pageable);
    UserGroupResponseDTO create(User user, UserGroupRequestDTO userGroupRequestDTO);
    UserGroupResponseDTO update(Long userGroupId, UserGroupRequestDTO userGroupRequestDTO);
    UserGroupResponseDTO get(Long id);
    void delete(Long id);
}

