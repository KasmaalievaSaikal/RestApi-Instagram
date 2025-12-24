package java19.api;


import jakarta.validation.Valid;
import java19.dto.auth.AuthResponse;
import java19.dto.auth.request.SignInRequest;
import java19.dto.auth.request.SignUpRequest;
import java19.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthApi {

    private final AuthService authService;

    @PostMapping("/signIn")
    public AuthResponse signIn(@Valid @RequestBody SignInRequest signInRequest) {
        return authService.signIn(signInRequest);
    }

    @PostMapping("/signUp")
    public AuthResponse signUpUser(@Valid @RequestBody SignUpRequest userSignUpRequest) {
        return authService.signUp(userSignUpRequest);
    }
}
