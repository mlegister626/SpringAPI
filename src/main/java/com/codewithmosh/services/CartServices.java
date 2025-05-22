package com.codewithmosh.services;

import com.codewithmosh.dtos.AddItemToCartRequest;
import com.codewithmosh.dtos.CartDto;
import com.codewithmosh.dtos.CartItemsDto;
import com.codewithmosh.entities.entities.Cart;
import com.codewithmosh.mappers.CartMapper;
import com.codewithmosh.repositories.repositories.CartRepository;
import com.codewithmosh.repositories.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CartServices {
    private CartRepository cartRepository;
    private CartMapper cartMapper;
    private ProductRepository productRepository;

    public CartDto getCart(UUID id) {
        var cart = cartRepository.findById(id).orElse(null);
        if(cart == null){
            throw new RuntimeException("The cart was not found");
        }
        return cartMapper.toDto(cart);
    }

    public CartDto addCart() {
        var cart = new Cart();
        cartRepository.save(cart);
        return cartMapper.toDto(cart);
    }

    public CartItemsDto addToCart(UUID cartId, Long productId){
        var cart = cartRepository.getCartsWithItems(cartId).orElse(null);
        if(cart == null){
            throw new RuntimeException("The cart was not found");
        }
        var product = productRepository.findById(productId).orElse(null);
        if(product == null){
            throw new RuntimeException("The product was not found");
        }
        //i need to find this object in my cart, and then add the quantity they null
        var cartItem = cart.addItem(product);//better domain logic, its better to have a rich domain then a crazy one

        cartRepository.save(cart);
        return cartMapper.toCartItemsDto(cartItem);

    }
    public CartItemsDto updateCartItem(UUID cartId, Long productId, AddItemToCartRequest request){
        var cart = cartRepository.findById(cartId).orElse(null);
        if(cart == null){
             throw new RuntimeException("The cart was not found");
        }
        var cartItem = cart.getItem(productId);
        if(cartItem == null){
            throw new RuntimeException("The cart item was not found");
        }
        if(request.getQuantity() < 1 || request.getQuantity() > 100){
            throw new RuntimeException("The quantity is out of range (1-100");
        }
        cartItem.setQuantity(request.getQuantity() + cartItem.getQuantity());
        cartRepository.save(cart);
        return cartMapper.toCartItemsDto(cartItem);
    }
    public void deleteCartItem(UUID cartId, Long productId){
        var cart = cartRepository.findById(cartId).orElse(null);
        if (cart == null){
            throw new RuntimeException("The cart was not found");
        }
        cart.removeItem(productId);
        cartRepository.save(cart);
    }
    public void clearCart(UUID cartId){
        var cart = cartRepository.findById(cartId).orElse(null);
        if (cart == null){
            throw new RuntimeException("The cart was not found");
        }
        cart.clearCart();
    }
}
