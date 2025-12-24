package java19.service;

import java19.dto.SimpleResponse;
import java19.dto.imageDto.AvatarRequest;
import java19.dto.userInfoDto.request.UserInfoRequest;
import java19.dto.userInfoDto.request.UserInfoUpdate;
import java19.dto.userInfoDto.response.UserInfoResponse;

public interface UserInfoService {

    UserInfoResponse saveUserInfo(UserInfoRequest userInfoRequest);

    UserInfoResponse findUserInfoByUserId(Long userId);

    SimpleResponse updateUserInfo(Long userId, UserInfoUpdate userInfoUpdate);

    SimpleResponse changeImage(Long userId, AvatarRequest avatar);

    SimpleResponse deleteImage(Long userId);
}
