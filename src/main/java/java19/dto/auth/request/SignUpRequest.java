package java19.dto.auth.request;

import java19.enums.Role;

public record SignUpRequest(
        String userName,
        String email,
        String password,
        String phoneNumber,
        Role role
) {
}
