package com.codewithmosh.dtos;

import com.codewithmosh.validation.Lowercase;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterUserRequest {
    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name must be less than 255 characters")
    private String name;
    @Email(message = "Email must be valid")
    @NotBlank(message = "Email is required")
    @Lowercase(message  = "Email must be lowercase")
    private String email;
    @NotBlank
    @Size(min = 6, max = 25,  message = "Password must be at least 6 characters and less than 25.")
    private String password;
}
