package com.codewithmosh.services;

import com.codewithmosh.entities.User;
import com.codewithmosh.repositories.CartRepository;
import com.codewithmosh.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
@AllArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;

    public User getCurrentUser(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userId = (Long)authentication.getPrincipal();
        var user = userRepository.findById(userId).orElse(null);
        return user;
    }
}
