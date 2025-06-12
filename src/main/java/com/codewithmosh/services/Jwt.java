package com.codewithmosh.services;

import com.codewithmosh.entities.entities.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;


@Service

public class Jwt {

    private final Claims claims;
    private final SecretKey secretKey;

    Jwt(Claims claims, SecretKey secretKey){
        this.claims = claims;
        this.secretKey = secretKey;
    }

    public boolean isExpired() {
            return claims.getExpiration().before(new Date());
    }
    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    public Long getUserIDFromToken() {
        return Long.valueOf(claims.getSubject());
    }
    public Role getRoleFromToken(){
        return Role.valueOf(claims.get("role", String.class));
    }
    public String tokenToString(String token){
        return Jwts.builder().claims(claims).signWith(secretKey).compact();
    }
}
