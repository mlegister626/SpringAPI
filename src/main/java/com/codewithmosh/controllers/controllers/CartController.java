package com.codewithmosh.controllers.controllers;

import com.codewithmosh.dtos.CartDto;
import com.codewithmosh.dtos.CartItemsDto;
import com.codewithmosh.entities.entities.Cart;
import com.codewithmosh.entities.entities.Product;
import com.codewithmosh.mappers.CartMapper;
import com.codewithmosh.mappers.ProductMapper;
import com.codewithmosh.repositories.repositories.CartItemsRepository;
import com.codewithmosh.repositories.repositories.CartRepository;
import com.codewithmosh.repositories.repositories.ProductRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
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

    //    @GetMapping("/{id}")
//    public ResponseEntity<CartDto> getCart(@PathVariable UUID id) {
//        var cart = cartRepository.getCartsById(id).stream().findFirst().orElse(null);
//        if(cart == null){
//            return ResponseEntity.notFound().build();
//        }
//        var cartDto = new CartDto(cart.getId(), cart.getDate());
//        return ResponseEntity.ok(cartDto);
//    }
    //next I must create my cart for my items
    @PostMapping("")
    public ResponseEntity<?> createCart(UriComponentsBuilder uriComponentsBuilder){
        var cart = new Cart();
        cartRepository.save(cart);

        var cartDto = cartMapper.toDto(cart);
        var uri = uriComponentsBuilder.path("/carts/{id}").buildAndExpand(cartDto.getId()).toUri();
        return ResponseEntity.created(uri).body(cartDto);
    }
    @PostMapping("{cartId}/items")
    public ResponseEntity<?> addToCart(@RequestBody CartItemsDto cartItemsDto, @PathVariable UUID cartId){
        if(cartRepository.findCartById(cartId) == null){
            return ResponseEntity.badRequest().build();
        }
        if(productRepository.findById(cartItemsDto.getProductId()) == null){
            return ResponseEntity.notFound().build();
        }
        //i need to find this object in my cart, and then add the quantity they null
        return null;
    }
}
