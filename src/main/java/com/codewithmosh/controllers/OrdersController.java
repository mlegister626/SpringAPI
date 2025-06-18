package com.codewithmosh.controllers;

import com.codewithmosh.dtos.OrdersDto;
import com.codewithmosh.repositories.OrdersRepository;
import com.codewithmosh.repositories.UserRepository;
import com.codewithmosh.services.AuthService;
import io.jsonwebtoken.lang.Maps;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrdersController {

    private final UserRepository userRepository;
    private final AuthService authService;
    private final OrdersRepository ordersRepository;

    @GetMapping("")
    public ResponseEntity<?> getAllOrders(){
       var userId = authService.getCurrentUser().getId();
       var user = userRepository.findById(userId).orElse(null);
       if(user == null){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Maps.of("error", "User not found."));
       }
       var orders = ordersRepository.findOrdersByCustomerId(userId).orElse(null);
       if(orders == null){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Maps.of("error", "Orders not found."));
       }
       return null;
    }
}
