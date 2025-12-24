package java19.dto.userInfoDto.response;

import java19.enums.Gender;
import lombok.Builder;

@Builder
public record UserInfoResponse(
        String fullName,
        String biography,
        Gender gender,
        String image) {
}
