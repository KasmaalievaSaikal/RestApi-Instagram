package java19.service;

import java19.dto.auth.AuthResponse;
import java19.dto.auth.request.SignInRequest;
import java19.dto.auth.request.SignUpRequest;

public interface AuthService {

    AuthResponse signUp(SignUpRequest signUpRequest);

    AuthResponse signIn(SignInRequest signInRequest);
}
