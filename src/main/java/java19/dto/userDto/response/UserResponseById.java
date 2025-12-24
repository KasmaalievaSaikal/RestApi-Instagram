package java19.dto.userDto.response;

import lombok.Builder;

@Builder
public record UserResponseById(
        String username,
        String email,
        String phoneNumber) {
}
