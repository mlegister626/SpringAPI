package com.codewithmosh.controllers;

import com.codewithmosh.dtos.CheckoutRequest;
import com.codewithmosh.dtos.CheckoutResponse;
import com.codewithmosh.dtos.ErrorDto;
import com.codewithmosh.entities.OrderItem;
import com.codewithmosh.entities.OrderStatus;
import com.codewithmosh.entities.Orders;
import com.codewithmosh.exceptions.CartNotFoundException;
import com.codewithmosh.repositories.CartRepository;
import com.codewithmosh.repositories.OrdersRepository;
import com.codewithmosh.services.AuthService;
import com.codewithmosh.services.CartServices;
import com.codewithmosh.services.CheckoutService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/checkout")
public class CheckoutController {

    private final CheckoutService checkoutService;

    @PostMapping()
    public CheckoutResponse checkout(@RequestBody CheckoutRequest requestBody){
        return checkoutService.checkout(requestBody);
    }
    @ExceptionHandler({CartNotFoundException.class, CartNotFoundException.class})
    public ResponseEntity<ErrorDto> handleCartNotFound(CartNotFoundException ex){
        return ResponseEntity.badRequest().body(new ErrorDto(ex.getMessage()));
    }
}
