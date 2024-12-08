package org.example.authentication.application.impl;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.example.authentication.application.JWTService;
import org.example.user.domain.User;
import org.example.user.dto.ResponseUserDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class JWTServiceImpl implements JWTService {
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private Long jwtExpiration;


    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public Long extractUserId(String token) {
        return extractClaim(token, claims -> claims.get("user_id", Long.class));
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    @Override
    public String generateToken(User userDetails) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("user_id", userDetails.getId());
        extraClaims.put("customer_id", userDetails.getCustomer().getId());
        extraClaims.put("sector", userDetails.getCustomer().getSector());
        extraClaims.put("role", userDetails.getCustomer().getUser().getRole());
        extraClaims.put("version", userDetails.getCustomer().getVersion());

        return Jwts.builder().setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public Boolean isTokenValid(String token, User userDetails) {
        String username = extractUsername(token);
        Long tokenVersion = extractAllClaims(token).get("version", Long.class);
        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token)
                && userDetails.getCustomer().getVersion().equals(tokenVersion);
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}