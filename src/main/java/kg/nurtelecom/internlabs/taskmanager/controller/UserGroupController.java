package kg.nurtelecom.internlabs.taskmanager.controller;

import jakarta.validation.Valid;
import kg.nurtelecom.internlabs.taskmanager.model.User;
import kg.nurtelecom.internlabs.taskmanager.payload.usergroupdto.UserGroupResponseDTO;
import kg.nurtelecom.internlabs.taskmanager.payload.usergroupdto.UserGroupRequestDTO;
import kg.nurtelecom.internlabs.taskmanager.service.UserGroupService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/groups")
@CrossOrigin(origins = {"http://localhost:8081", "http://localhost:8816"})
public class UserGroupController {
    private final UserGroupService userGroupService;

    public UserGroupController(UserGroupService userGroupService) {
        this.userGroupService = userGroupService;
    }

    @PreAuthorize("hasRole('DEVELOPER') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<UserGroupResponseDTO> createUserGroup(@AuthenticationPrincipal User user, @Valid @RequestBody UserGroupRequestDTO userGroupRequestDTO){
        UserGroupResponseDTO createdUserGroupResponseDTO = userGroupService.create(user, userGroupRequestDTO);
        return new ResponseEntity<>(createdUserGroupResponseDTO,HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('DEVELOPER') or hasRole('ADMIN')")
    @PutMapping("/{userGroupId}")
    public ResponseEntity<UserGroupResponseDTO> updateUserGroup(@AuthenticationPrincipal User user, @PathVariable Long userGroupId, @Valid @RequestBody UserGroupRequestDTO userGroupRequestDTO) {
        UserGroupResponseDTO userGroupResponseDTO = userGroupService.update(userGroupId, userGroupRequestDTO);
        return new ResponseEntity<>(userGroupResponseDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('DEVELOPER') or hasRole('ADMIN')")
    @GetMapping("/{userGroupId}")
    public ResponseEntity<UserGroupResponseDTO> getUserGroup(@PathVariable Long userGroupId) {
        UserGroupResponseDTO userGroupResponseDTO = userGroupService.get(userGroupId);
        return new ResponseEntity<>(userGroupResponseDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('DEVELOPER') or hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserGroupResponseDTO>> getUserGroups(
            @PageableDefault(page = 0, size = 10, sort = "userGroupId", direction = Sort.Direction.ASC) Pageable pageable) {

        Page<UserGroupResponseDTO> userGroupPage = userGroupService.getUserGroups(pageable);
        //Временное решение. Надо будет на фронте /projects сделать пагинацию
        List<UserGroupResponseDTO> userGroupList = userGroupPage.getContent();
        return new ResponseEntity<>(userGroupList, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userGroupId}")
    public ResponseEntity<UserGroupResponseDTO> deleteUserGroup(@PathVariable Long userGroupId) {
        userGroupService.delete(userGroupId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
