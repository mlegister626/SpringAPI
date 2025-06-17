package com.codewithmosh.controllers;

import com.codewithmosh.dtos.AddItemToCartRequest;
import com.codewithmosh.dtos.CartDto;
import com.codewithmosh.dtos.CartItemsDto;
import com.codewithmosh.dtos.CheckoutRequest;
import com.codewithmosh.entities.Orders;
import com.codewithmosh.exceptions.CartNotFoundException;
import com.codewithmosh.repositories.CartRepository;
import com.codewithmosh.repositories.OrdersRepository;
import com.codewithmosh.services.CartServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/cart")
@AllArgsConstructor
@Tag(name = "Carts")
public class CartController {
    private final CartServices cartServices;

    @GetMapping("/{id}")
    public CartDto getCart(@PathVariable UUID id) {
            return cartServices.getCart(id);
    }
    @PostMapping("")
    public ResponseEntity<?> createCart(UriComponentsBuilder uriComponentsBuilder){

        var cartDto = cartServices.addCart();
        var uri = uriComponentsBuilder.path("/carts/{id}").buildAndExpand(cartDto.getId()).toUri();
        return ResponseEntity.created(uri).body(cartDto);
    }
    @PostMapping("{cartId}/items")
    @Operation(summary = "Added Items to the cart")
    public ResponseEntity<CartItemsDto> addToCart(
           @Parameter(description = "The Id of the cart.") @RequestBody AddItemToCartRequest request,
                                                  @PathVariable UUID cartId){
        var cartItemDto = cartServices.addToCart(cartId, request.getProductId());
        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemDto);
    }
    @PutMapping("{cartId}/items/{productId}")
    public CartItemsDto updateCart(@RequestBody AddItemToCartRequest request,
                                                   @PathVariable Long productId,
                                                   @PathVariable UUID cartId){
            return cartServices.updateCartItem(cartId, productId, request);
    }
    @DeleteMapping("{cartId}/items/{productId}/delete")
    public ResponseEntity<?> deleteCartItem(@PathVariable UUID cartId, @PathVariable Long productId){
            cartServices.deleteCartItem(cartId, productId);
            return ResponseEntity.noContent().build();
    }
    @DeleteMapping("{cartId}/items")
    public ResponseEntity<?> clearCart(@PathVariable UUID cartId){
            cartServices.clearCart(cartId);
            return ResponseEntity.noContent().build();
    }
    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCartNotFound(UUID cartId){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Cart not found."));
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleProductNotFound(Long productId){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Product not found in the cart."));
    }
}
