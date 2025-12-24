package java19.service.impl;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import java19.configuration.jwt.JwtService;
import java19.dto.auth.AuthResponse;
import java19.dto.auth.request.SignInRequest;
import java19.dto.auth.request.SignUpRequest;
import java19.entity.User;
import java19.enums.Role;
import java19.excepion.BadCredentialsException;
import java19.excepion.NotFoundException;
import java19.repository.FollowerRepository;
import java19.repository.UserRepository;
import java19.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse signUp(SignUpRequest signUpRequest) {
        if (userRepository.findUserByEmail(signUpRequest.email()).isPresent()) {
            throw new BadCredentialsException(String.format("User with email %s already exists", signUpRequest.email()));
        }
        User user = User
                .builder()
                .userName(signUpRequest.userName())
                .password(passwordEncoder.encode(signUpRequest.password()))
                .email(signUpRequest.email())
                .phoneNumber(signUpRequest.phoneNumber())
                .role(signUpRequest.role())
                .followersCount(0)
                .followingCount(0)
                .build();
        userRepository.save(user);

        String tokenUser = jwtService.generateToken(user);
        return AuthResponse
                .builder()
                .id(user.getId())
                .token(tokenUser)
                .role(user.getRole())
                .build();
    }


    @Override
    public AuthResponse signIn(SignInRequest signInRequest) {
        User user = userRepository.findUserByEmail(signInRequest.email()).orElseThrow(() ->
                new NotFoundException("User with email " + signInRequest.email() + " not found"));
        if (!passwordEncoder.matches(signInRequest.password(), user.getPassword())) {
            throw new BadCredentialsException(String.format("Passwords do not match"));
        }
        String token = jwtService.generateToken(user);
        return AuthResponse
                .builder()
                .id(user.getId())
                .token(token)
                .role(user.getRole())
                .build();
    }


    //init method
    @PostConstruct
    private void saveAdmin() {
        User user = new User();
        user.setUserName("admin");
        user.setPassword(passwordEncoder.encode("admin1234"));
        user.setEmail("admin@gmail.com");
        user.setPhoneNumber("+996500343536");
        user.setRole(Role.ADMIN);
        if (!userRepository.existsUserByEmail(user.getEmail())) {
            userRepository.save(user);
        }
    }
}
