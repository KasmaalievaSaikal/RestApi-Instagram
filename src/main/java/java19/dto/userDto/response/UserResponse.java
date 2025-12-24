package java19.dto.userDto.response;

import lombok.Builder;

@Builder
public record UserResponse(

        Long id,
        String userName,
        String email,
        String phoneNumber) {
}
