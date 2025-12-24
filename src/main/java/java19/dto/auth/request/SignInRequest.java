package java19.dto.auth.request;

public record SignInRequest(
        String email,
        String password
) {
}
