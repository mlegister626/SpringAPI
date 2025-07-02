package com.codewithmosh.auth;

import com.codewithmosh.common.ErrorDto;
import com.codewithmosh.users.UserDto;
import com.codewithmosh.users.UserMapper;
import com.codewithmosh.users.UserNotFoundException;
import com.codewithmosh.users.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jWTService;
    private final JwtConfig jwtConfig;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletResponse response) {
        return ResponseEntity.ok(authService.loginRequest(request, response));
    }
    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refresh(@CookieValue(value = "refreshToken") String refreshToken){
        return ResponseEntity.ok(authService.refreshToken(refreshToken));
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> me (){

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userId = (Long)authentication.getPrincipal();
        var user = userRepository.findById(userId).orElse(null);
        if (user == null){
            return ResponseEntity.notFound().build();
        }
        var userDto = userMapper.toDto(user);

        return ResponseEntity.ok(userDto);
    }
    @ExceptionHandler(TokenIncompatibilityException.class)
    public ResponseEntity<ErrorDto> handleTokenIncompatibility(){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorDto("There was an error in the refresh token please try again another time."));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDto> handleUserNotFound(){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorDto("There was an error retrieving the user profile try again later."));
    }

}
