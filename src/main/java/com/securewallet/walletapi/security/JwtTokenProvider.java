package com.securewallet.walletapi.security;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
@Component
public class JwtTokenProvider {
@Value("${security.jwt.secret}")
private String secret;

@Value("${security.jwt.expiration}")
private long expirationMs;

public String generateToken(String email) {
    Instant expiresAt = LocalDateTime.now()
        .plusSeconds(expirationMs / 1000)
        .toInstant(ZoneOffset.of("-03:00"));

    return JWT.create()
        .withSubject(email)
        .withExpiresAt(expiresAt)
        .sign(Algorithm.HMAC256(secret));
}

public String validateTokenAndGetSubject(String token) {
    try {
        return JWT.require(Algorithm.HMAC256(secret))
            .build()
            .verify(token)
            .getSubject();
    } catch (JWTVerificationException e) {
        return null;
    }
}
}
