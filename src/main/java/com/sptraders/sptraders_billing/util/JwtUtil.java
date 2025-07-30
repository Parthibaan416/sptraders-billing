package com.sptraders.sptraders_billing.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtUtil {

    // Inject the secret key from application.properties or config server
    @Value("${jwt.secret}")
    private String jwtSecret;

    // Holds the cryptographic Key derived from secret string
    private Key key;

    /**
     * Initializes the cryptographic key after bean construction.
     * Converts the secret string into a suitable HMAC SHA key.
     */
    @PostConstruct
    public void init() {
        // It’s important the secret is long enough (at least 256 bits) for HS256
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    /**
     * Generates a JWT token containing username and role claims.
     * Token validity is set to 1 day.
     *
     * @param username the username to store in token subject
     * @param role     the role claim to include in the token
     * @return signed JWT token string
     */
    public String generateToken(String username, String role) {
        Instant now = Instant.now();

        return Jwts.builder()
                .setSubject(username)                         // 'sub' claim
                .claim("role", role)                          // custom 'role' claim
                .setIssuedAt(Date.from(now))                  // 'iat'
                .setExpiration(Date.from(now.plus(1, ChronoUnit.DAYS)))  // 'exp'
                .signWith(key, SignatureAlgorithm.HS256)     // sign using HS256 + key
                .compact();
    }

    /**
     * Extracts all claims from the JWT token (validates signature).
     *
     * @param token the JWT token string
     * @return Claims object containing token payload
     * @throws JwtException if token is invalid or expired
     */
    public Claims extractAllClaims(String token) throws JwtException {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Extracts username (subject) from the JWT token.
     *
     * @param token the JWT token string
     * @return username stored in 'sub' claim
     */
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    /**
     * Extracts the role claim from the JWT token.
     *
     * @param token the JWT token string
     * @return role claim value as String
     */
    public String extractRole(String token) {
        return (String) extractAllClaims(token).get("role");
    }

    /**
     * Validates the JWT token signature and expiration.
     *
     * @param token the JWT token string
     * @return true if token is valid, false otherwise
     */
    public boolean validateToken(String token) {
        try {
            extractAllClaims(token); // Will throw if token invalid/expired
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // Optionally log the exception message here
            return false;
        }
    }
}



