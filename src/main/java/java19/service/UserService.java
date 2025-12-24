package java19.service;

import java19.dto.SimpleResponse;
import java19.dto.userDto.request.UserUpdateRequest;
import java19.dto.userDto.response.UserProfileResponse;
import java19.dto.userDto.response.UserResponse;
import java19.dto.userDto.response.UserResponseById;
import java19.dto.userDto.response.UserUpdateResponse;


import java.util.List;

public interface UserService {

    UserResponseById getUserById(Long id);

    List<UserResponse> getAllUsers();

    UserUpdateResponse updateUser(Long id, UserUpdateRequest userUpdateRequest);

    SimpleResponse deleteUserById(Long id);

    UserProfileResponse userProfile(Long id);
}