package kg.nurtelecom.internlabs.taskmanager.controller;

import jakarta.validation.Valid;
import kg.nurtelecom.internlabs.taskmanager.model.User;
import kg.nurtelecom.internlabs.taskmanager.payload.userdto.UserRequestDTO;
import kg.nurtelecom.internlabs.taskmanager.payload.userdto.UserResponseDTO;
import kg.nurtelecom.internlabs.taskmanager.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = {"http://localhost:8081", "http://localhost:8816"})
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('DEVELOPER') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        UserResponseDTO userResponseDTO = userService.create(userRequestDTO);
        return new ResponseEntity<>(userResponseDTO, HttpStatus.CREATED);
    }


    @PreAuthorize("hasRole('DEVELOPER') or hasRole('ADMIN')")
    @PutMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> editUser(@PathVariable Long userId, @Valid @RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO userUserResponseDTO = userService.update(userId, userRequestDTO);
        return new ResponseEntity<>(userUserResponseDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') or hasRole('DEVELOPER') or hasRole('ADMIN')")
    @PostMapping("/upload-avatar")
    public ResponseEntity<String> uploadAvatar(@AuthenticationPrincipal User user, @RequestParam("file") MultipartFile file) {
        String uploadedImageUrl = userService.uploadUserAvatar(user, file);
        return new ResponseEntity<>(uploadedImageUrl, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') or hasRole('DEVELOPER') or hasRole('ADMIN')")
    @GetMapping(value = "/{userId}", produces = "application/json")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable Long userId) {
        UserResponseDTO userResponseDTO = userService.get(userId);
        return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') or hasRole('DEVELOPER') or hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getUsers(@PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC)
                                                          Pageable pageable) {
        Page<UserResponseDTO> users = userService.getAllUsers(pageable);
        List<UserResponseDTO> userResponseDTOList = users.getContent();
        return new ResponseEntity<>(userResponseDTOList, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') or hasRole('DEVELOPER') or hasRole('ADMIN')")
    @GetMapping("projects/{projectId}")
    public ResponseEntity<List<UserResponseDTO>> getUsersByProject(@PathVariable Long projectId) {
        return new ResponseEntity<>(userService.getUsersByProject(projectId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> deleteUser(@PathVariable Long userId) {
        userService.delete(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
