package com.codewithmosh.services;

import com.codewithmosh.config.JwtConfig;
import com.codewithmosh.entities.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
@AllArgsConstructor
@Service
public class JwtService {
    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;

    public Jwt generateAccessToken(User user) {
        final long tokenExpiration = 300;
        return generateToken(user, jwtConfig.getAccessTokenExpiration());
    }
    public Jwt generateRefreshToken(User user) {
        final long tokenExpiration = 604800;
        return generateToken(user, jwtConfig.getRefreshTokenExpiration());
    }

    private Jwt generateToken(User user, long tokenExpiration) {
        var claims = Jwts.claims()
                .subject(user.getId().toString())
                .add("email", user.getEmail())
                .add("role", user.getRole())
                .add("name", user.getName())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * tokenExpiration))
                .build();

        return new Jwt(claims, jwtConfig.getSecretKey());


    }
    public Jwt parseTokens(String token){
        try {
            var claims = getClaims(token);
            return new Jwt(claims, jwtConfig.getSecretKey());
        }
        catch(Exception e){
            return null;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
