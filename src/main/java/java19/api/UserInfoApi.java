package java19.api;


import java19.dto.SimpleResponse;
import java19.dto.imageDto.AvatarRequest;
import java19.dto.userInfoDto.request.UserInfoRequest;
import java19.dto.userInfoDto.request.UserInfoUpdate;
import java19.dto.userInfoDto.response.UserInfoResponse;
import java19.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/userInfos")
@RequiredArgsConstructor
public class UserInfoApi {

    private final UserInfoService userInfoService;

    @PostMapping("/save-userInfo")
    @PreAuthorize("hasRole('USER')")
    public UserInfoResponse addUserInfo(@RequestBody UserInfoRequest userInfoRequest) {
        return userInfoService.saveUserInfo(userInfoRequest);
    }

    @GetMapping("/{userId}/get-info")
    public UserInfoResponse getUserInfo(@PathVariable Long userId) {
        return userInfoService.findUserInfoByUserId(userId);
    }

    @PutMapping("/{userId}/update-info")
    @PreAuthorize("hasRole('USER')")
    public SimpleResponse updateInfo(@PathVariable Long userId, @RequestBody UserInfoUpdate infoUpdateRequest) {
        return userInfoService.updateUserInfo(userId, infoUpdateRequest);
    }

    @PutMapping("/{userId}/change-avatar")
    @PreAuthorize("hasRole('USER')")
    public SimpleResponse updateUserImage(@PathVariable Long userId, @RequestBody AvatarRequest image) {
        return userInfoService.changeImage(userId, image);
    }

    @DeleteMapping("/{userId}/delete-userImage")
    @PreAuthorize("hasRole('USER')")
    public SimpleResponse deleteUserImage(@PathVariable Long userId) {
        return userInfoService.deleteImage(userId);
    }
}
