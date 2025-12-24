package java19.dto.userDto.request;

public record UserUpdateRequest(
        String userName,
        String email,
        String password,
        String phoneNumber) {
}
