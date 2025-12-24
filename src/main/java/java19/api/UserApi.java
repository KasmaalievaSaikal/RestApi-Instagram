package java19.api;

import java19.dto.SimpleResponse;
import java19.dto.userDto.request.UserUpdateRequest;
import java19.dto.userDto.response.UserProfileResponse;
import java19.dto.userDto.response.UserResponse;
import java19.dto.userDto.response.UserResponseById;
import java19.dto.userDto.response.UserUpdateResponse;
import java19.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserApi {

    private final UserService userService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponseById getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/getAll-users")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/{id}/update")
    @PreAuthorize("hasRole('USER')")
    public UserUpdateResponse updateUser(@PathVariable Long id, @RequestBody UserUpdateRequest userUpdateRequest) {
        return userService.updateUser(id, userUpdateRequest);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public SimpleResponse deleteUser(@PathVariable Long id) {
        return userService.deleteUserById(id);
    }

    @GetMapping("/{id}/user-profile")
    public UserProfileResponse getProfile(@PathVariable Long id) {
        return userService.userProfile(id);
    }
}
