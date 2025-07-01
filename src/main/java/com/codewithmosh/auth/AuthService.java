package com.codewithmosh.auth;

import com.codewithmosh.users.User;
import com.codewithmosh.users.UserNotFoundException;
import com.codewithmosh.users.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.management.remote.JMXAuthenticator;

@AllArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jWTService;
    private final JwtConfig jwtConfig;

    public User getCurrentUser(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userId = (Long)authentication.getPrincipal();
        return userRepository.findById(userId).orElse(null);
    }

    public JwtResponse loginRequest(LoginRequest request, HttpServletResponse response){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new UserNotFoundException("User was not found"));

        var accessToken = jWTService.generateAccessToken(user);
        var refreshToken = jWTService.generateRefreshToken(user);
        var cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/auth/refresh");
        cookie.setMaxAge(jwtConfig.getRefreshTokenExpiration());
        cookie.setSecure(true);
        response.addCookie(cookie);
        return new JwtResponse(accessToken);
    }

    public JwtResponse refreshToken(String refreshToken){
        var jwt = jWTService.parseTokens(refreshToken);
        if(jwt == null|| jwt.isExpired()){
            throw new TokenIncompatibilityException("The token could not be refreshed due to it being expire or null.");
        }
        var user = userRepository.findById(jwt.getUserIDFromToken()).orElse(null);
        if (user == null){
            throw new UserNotFoundException("User not found");
        }
        var accessToken = jWTService.generateAccessToken(user);
        return new JwtResponse(accessToken);
    }
}
