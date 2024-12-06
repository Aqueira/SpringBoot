package org.example.authentication.application;

import io.jsonwebtoken.Claims;
import org.example.user.domain.User;
import org.example.user.dto.ResponseUserDTO;

import java.util.function.Function;

public interface JWTService {
    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    String extractUsername(String token);

    String generateToken(User user);

    Boolean isTokenValid(String token, User user);

    Long extractCustomerId(String token);
}
