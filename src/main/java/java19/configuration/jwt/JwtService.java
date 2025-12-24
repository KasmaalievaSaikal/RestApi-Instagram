package java19.configuration.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java19.entity.User;
import java19.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
@RequiredArgsConstructor
public class JwtService {

    private final UserRepository userRepo;

    @Value("${security.secret.key}")
    private String secretKey;

    // create token
    public String generateToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.create()
                .withClaim("id", user.getId())
                .withClaim("email", user.getEmail())
                .withClaim("role", user.getRole().name())
                .withIssuedAt(ZonedDateTime.now().toInstant())
                .withExpiresAt(ZonedDateTime.now().plusHours(1).toInstant())
                .sign(algorithm);
    }

    // proverka tokena
    public User verifyToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJwt = verifier.verify(token);
        String email = decodedJwt.getClaim("email").asString();
        return userRepo.findUserByEmail(email).orElseThrow(() ->
                new RuntimeException(String.format("User with email %s not found", email)));
    }

    public User checkAuthentication() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepo.findUserByEmail(email).orElseThrow(() ->
                new NullPointerException(String.format("User with email %s does not exists", email)));
    }
}