package com.codewithmosh.controllers;

import com.codewithmosh.dtos.CheckoutRequest;
import com.codewithmosh.dtos.CheckoutResponse;
import com.codewithmosh.dtos.ErrorDto;
import com.codewithmosh.exceptions.CartNotFoundException;
import com.codewithmosh.services.CheckoutService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@AllArgsConstructor
@RequestMapping("/checkout")
public class CheckoutController {

    private final CheckoutService checkoutService;

    @PostMapping()
    public CheckoutResponse checkout(
           @Valid @RequestBody CheckoutRequest request){
        return checkoutService.checkout(request);
    }
    @ExceptionHandler({CartNotFoundException.class, CartNotFoundException.class})
    public ResponseEntity<ErrorDto> handleCartNotFound(CartNotFoundException ex){
        return ResponseEntity.badRequest().body(new ErrorDto(ex.getMessage()));
    }
}
