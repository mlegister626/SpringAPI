package com.codewithmosh.services;

import com.codewithmosh.dtos.CheckoutRequest;
import com.codewithmosh.dtos.CheckoutResponse;
import com.codewithmosh.entities.Orders;
import com.codewithmosh.exceptions.CartEmptyException;
import com.codewithmosh.exceptions.CartNotFoundException;
import com.codewithmosh.repositories.CartRepository;
import com.codewithmosh.repositories.OrdersRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CheckoutService {
    private final CartRepository cartRepository;
    private final OrdersRepository ordersRepository;
    private final AuthService authService;
    private final CartServices cartServices;

    public CheckoutResponse checkout(CheckoutRequest request){
        var cart = cartRepository.getCartsWithItems(request.getCartId()).orElse(null);
        if(cart == null){
            throw new CartNotFoundException();
        }
        if(cart.isEmpty()){
            throw new CartEmptyException();
        }
        Orders order = Orders.createOrder(cart, authService.getCurrentUser());
        ordersRepository.save(order);

        cartServices.clearCart(cart.getId());

        return new CheckoutResponse(order.getId());
    }
}
