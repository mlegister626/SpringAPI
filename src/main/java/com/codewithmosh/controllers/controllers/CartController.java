package com.codewithmosh.controllers.controllers;

import com.codewithmosh.dtos.AddItemToCartRequest;
import com.codewithmosh.dtos.CartDto;
import com.codewithmosh.dtos.CartItemsDto;
import com.codewithmosh.entities.entities.Cart;
import com.codewithmosh.entities.entities.CartItems;
import com.codewithmosh.mappers.CartMapper;
import com.codewithmosh.mappers.ProductMapper;
import com.codewithmosh.repositories.repositories.CartItemsRepository;
import com.codewithmosh.repositories.repositories.CartRepository;
import com.codewithmosh.repositories.repositories.ProductRepository;
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
public class CartController {
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CartItemsRepository cartItemsRepository;

        @GetMapping("/{id}")
    public ResponseEntity<CartDto> getCart(@PathVariable UUID id) {
            var cart = cartRepository.getCartsWithItems(id).orElse(null);
            if(cart == null){
                return ResponseEntity.notFound().build();
            }
            var cartDto = cartMapper.toDto(cart);
            return ResponseEntity.ok(cartDto);
    }
    @PostMapping("")
    public ResponseEntity<?> createCart(UriComponentsBuilder uriComponentsBuilder){
        var cart = new Cart();
        cartRepository.save(cart);

        var cartDto = cartMapper.toDto(cart);
        var uri = uriComponentsBuilder.path("/carts/{id}").buildAndExpand(cartDto.getId()).toUri();
        return ResponseEntity.created(uri).body(cartDto);
    }
    @PostMapping("{cartId}/items")
    public ResponseEntity<CartItemsDto> addToCart(@RequestBody AddItemToCartRequest request,
                                                  @PathVariable UUID cartId){
        var cart = cartRepository.getCartsWithItems(cartId).orElse(null);
        if(cart == null){
            return ResponseEntity.badRequest().build();
        }
        var product = productRepository.findById(request.getProductId()).orElse(null);
        if(product == null){
            return ResponseEntity.badRequest().build();
        }
        //i need to find this object in my cart, and then add the quantity they null
        var cartItem = cart.addItem(product);//better domain logic, its better to have a rich domain then a crazy one

        cartRepository.save(cart);
        var cartItemDto = cartMapper.toCartItemsDto(cartItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemDto);
    }
    @PutMapping("{cartId}/items/{productId}")
    public ResponseEntity<?> updateCart(@RequestBody AddItemToCartRequest request,
                                                   @PathVariable Long productId,
                                                   @PathVariable UUID cartId){
            var cart = cartRepository.findById(cartId).orElse(null);
            if(cart == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Cart not found."));
            }
            var product = productRepository.findById(productId).orElse(null);
            if(product == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Product not found."));
            }
            var cartItem = cart.getItem(productId);
            if(cartItem == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Cart item not found."));
            }
            if(request.getQuantity() < 1 || request.getQuantity() > 100){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Quantity must be between 1 and 100."));
            }
            cartItem.setQuantity(request.getQuantity() + cartItem.getQuantity());
            cartRepository.save(cart);
            var cartItemDto = cartMapper.toCartItemsDto(cartItem);
            return ResponseEntity.ok(cartItemDto);
    }
    @DeleteMapping("{cartId}/items/{productId}/delete")
    public ResponseEntity<?> deleteCartItem(@PathVariable UUID cartId, @PathVariable Long productId){
            var cart = cartRepository.findById(cartId).orElse(null);
            if (cart == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Invalid user"));
            }
            cart.removeItem(productId);
            cartRepository.save(cart);
            return ResponseEntity.noContent().build();

    }
    @DeleteMapping("{cartId}/items")
    public ResponseEntity<?> clearCart(@PathVariable UUID cartId){
            var cart = cartRepository.findById(cartId).orElse(null);
            if (cart == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Invalid user"));
            }
            cart.clearCart();
            cartRepository.save(cart);
            return ResponseEntity.noContent().build();
    }
}
