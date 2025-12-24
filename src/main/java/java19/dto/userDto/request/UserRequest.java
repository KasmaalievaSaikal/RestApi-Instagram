package java19.dto.userDto.request;

public record UserRequest(
        String userName,
        String password,
        String email,
        String phoneNumber
) {
}
