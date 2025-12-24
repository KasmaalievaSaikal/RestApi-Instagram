package java19.dto.userInfoDto.request;

import java19.enums.Gender;

public record UserInfoRequest(
        String fullName,
        String biography,
        Gender gender,
        String image) {
}
