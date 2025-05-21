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
    public ResponseEntity<CartDto> getCart(@PathVariable UUID id,
        @RequestBody UriComponentsBuilder uriComponent) {
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
    public ResponseEntity<CartItemsDto> addToCart(@RequestBody AddItemToCartRequest request, @PathVariable UUID cartId){
        var cart = cartRepository.getCartsWithItems(cartId).orElse(null);
        if(cart == null){
            return ResponseEntity.badRequest().build();
        }
        var product = productRepository.findById(request.getProductId()).orElse(null);
        if(product == null){
            return ResponseEntity.badRequest().build();
        }
        //i need to find this object in my cart, and then add the quantity they null
        var cartItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
        .findFirst().orElse(null);
        if(cartItem!=null){
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        }else{
            cartItem = new CartItems();
            cartItem.setProduct(product);
            cartItem.setQuantity(1);
            cartItem.setCartId(cart);
            cart.getItems().add(cartItem);
        }
        cartRepository.save(cart);
        var cartItemDto = cartMapper.toCartItemsDto(cartItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemDto);
    }

}
