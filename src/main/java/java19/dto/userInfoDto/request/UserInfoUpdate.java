package java19.dto.userInfoDto.request;

import java19.enums.Gender;

public record UserInfoUpdate(
        String fullName,
        String biography,
        Gender gender) {
}
