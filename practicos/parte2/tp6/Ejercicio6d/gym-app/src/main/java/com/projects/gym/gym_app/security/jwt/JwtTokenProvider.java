package com.projects.gym.gym_app.security.jwt;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import jakarta.annotation.PostConstruct;

@Component
public class JwtTokenProvider {

    private final String secret;
    private final long accessTokenExpirationMs;
    private final long refreshTokenExpirationMs;
    private SecretKey signingKey;

    public JwtTokenProvider(
            @Value("${app.security.jwt.secret}") String secret,
            @Value("${app.security.jwt.expiration-ms}") long accessTokenExpirationMs,
            @Value("${app.security.jwt.refresh-expiration-ms}") long refreshTokenExpirationMs) {
        this.secret = secret;
        this.accessTokenExpirationMs = accessTokenExpirationMs;
        this.refreshTokenExpirationMs = refreshTokenExpirationMs;
    }

    @PostConstruct
    void init() {
        try {
            this.signingKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        } catch (IllegalArgumentException ex) {
            throw new IllegalStateException("La propiedad app.security.jwt.secret debe representar una clave HMAC de al menos 32 bytes", ex);
        }
    }

    public String generateAccessToken(Authentication authentication) {
        return generateToken(authentication, accessTokenExpirationMs);
    }

    public String generateRefreshToken(Authentication authentication) {
        return generateToken(authentication, refreshTokenExpirationMs);
    }

    public String getUsernameFromToken(String token) {
        return parseClaims(token).getBody().getSubject();
    }

    public boolean isTokenValid(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    private String generateToken(Authentication authentication, long expirationMillis) {
        Instant now = Instant.now();
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        String authorities = principal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setSubject(principal.getUsername())
                .claim("roles", authorities)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusMillis(expirationMillis)))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    private Jws<Claims> parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token);
    }
}
