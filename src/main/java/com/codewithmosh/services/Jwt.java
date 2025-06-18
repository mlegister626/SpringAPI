package com.codewithmosh.services;

import com.codewithmosh.entities.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.util.Date;


public class Jwt {

    private final SecretKey secretKey;
    private final Claims claims;
    public Jwt(Claims claims, SecretKey secretKey) {
        this.secretKey = secretKey;
        this.claims = claims;
    }

    public boolean isExpired() {
            return claims.getExpiration().before(new Date());
    }

    public Long getUserIDFromToken() {
        return Long.valueOf(claims.getSubject());
    }
    public Role getRoleFromToken(){
        return Role.valueOf(claims.get("role", String.class));
    }
    public String tokenToString(){
        return Jwts.builder().claims(claims).signWith(secretKey).compact();
    }
}
