package kg.nurtelecom.internlabs.taskmanager.service.impl;

import kg.nurtelecom.internlabs.taskmanager.model.User;
import kg.nurtelecom.internlabs.taskmanager.payload.usergroupdto.UserGroupRequestDTO;
import kg.nurtelecom.internlabs.taskmanager.payload.usergroupdto.UserGroupResponseDTO;
import kg.nurtelecom.internlabs.taskmanager.exception.UserGroupNotFoundException;
import kg.nurtelecom.internlabs.taskmanager.mapper.UserGroupsMapper;
import kg.nurtelecom.internlabs.taskmanager.model.UserGroup;
import kg.nurtelecom.internlabs.taskmanager.repository.UserGroupRepository;
import kg.nurtelecom.internlabs.taskmanager.service.UserGroupService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserGroupServiceImpl implements UserGroupService{
    private final UserGroupRepository userGroupRepository;

    private final UserGroupsMapper userGroupsMapper;

    public UserGroupServiceImpl(UserGroupRepository userGroupRepository, UserGroupsMapper userGroupsMapper) {
        this.userGroupRepository = userGroupRepository;
        this.userGroupsMapper = userGroupsMapper;
    }

    @Override
    public UserGroupResponseDTO create(User user, UserGroupRequestDTO userGroupRequestDTO) {
        UserGroup userGroup = userGroupsMapper.toUserGroup(userGroupRequestDTO);
        userGroup.setCreator(user);
        return userGroupsMapper.toUserGroupResponse(userGroupRepository.save(userGroup));
    }


    @Override
    public UserGroupResponseDTO update(Long id, UserGroupRequestDTO userGroupRequestDTO) {
        UserGroup updatedUserGroup = userGroupRepository.findById(id)
                .orElseThrow(() -> new UserGroupNotFoundException("Такой группы не существует"));
        updatedUserGroup.setGroupName(userGroupRequestDTO.getGroupName());
        updatedUserGroup.setParentGroup(userGroupRequestDTO.getParentGroup());
        return userGroupsMapper.toUserGroupResponse(userGroupRepository.save(updatedUserGroup));
    }

    @Override
    public Page<UserGroupResponseDTO> getUserGroups(Pageable pageable) {
        Page<UserGroup> userGroupPage = userGroupRepository.findAll(pageable);
        return userGroupPage.map(userGroupsMapper::toUserGroupResponse);
    }

    @Override
    public UserGroupResponseDTO get(Long id) {
        UserGroup userGroup = userGroupRepository.findById(id)
                .orElseThrow(()-> new UserGroupNotFoundException("Группа с таким id не найдена!"));
        return userGroupsMapper.toUserGroupResponse(userGroup);
    }

    @Override
    public void delete(Long id) {
        userGroupRepository.deleteById(id);
    }
}
