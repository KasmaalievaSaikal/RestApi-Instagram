package java19.dto.auth;

import java19.enums.Role;
import lombok.Builder;

@Builder
public record AuthResponse(
        Long id,
        String token,
        Role role
) {
}
