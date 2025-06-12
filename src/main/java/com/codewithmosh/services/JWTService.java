package com.codewithmosh.services;

import com.codewithmosh.config.JwtConfig;
import com.codewithmosh.entities.entities.Role;
import com.codewithmosh.entities.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
@AllArgsConstructor
@Service
public class JWTService {
    private final JwtConfig jwtConfig;


    public String generateAccessToken(User user) {
        final long tokenExpiration = 300;
        return generateToken(user, jwtConfig.getAccessTokenExpiration());
    }
    public String generateRefreshToken(User user) {
        final long tokenExpiration = 604800;
        return generateToken(user, jwtConfig.getRefreshTokenExpiration());
    }

    private String generateToken(User user, long tokenExpiration) {
        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("email", user.getName())
                .claim("role", user.getRole())
                .claim("id", user.getId())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * tokenExpiration))
                .signWith(jwtConfig.getSecretKey())
                .compact();
    }

    public boolean validateToken(String token) {
        try{
            var claims = getClaims(token);

            return claims.getExpiration().after(new Date());
        }
        catch(JwtException e){
            return false;
        }

    }
    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(jwtConfig.getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    public Long getUserIDFromToken(String token) {
        return Long.valueOf(getClaims(token).getSubject());
    }
    public Role getRoleFromToken(String token){
        return Role.valueOf(getClaims(token).get("role", String.class));
    }

}
