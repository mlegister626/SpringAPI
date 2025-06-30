package com.codewithmosh.services;

import com.codewithmosh.dtos.CheckoutRequest;
import com.codewithmosh.dtos.CheckoutResponse;
import com.codewithmosh.entities.Orders;
import com.codewithmosh.exceptions.CartEmptyException;
import com.codewithmosh.exceptions.CartNotFoundException;
import com.codewithmosh.exceptions.PaymentException;
import com.codewithmosh.repositories.CartRepository;
import com.codewithmosh.repositories.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class CheckoutService {
    private final CartRepository cartRepository;
    private final OrdersRepository ordersRepository;
    private final AuthService authService;
    private final CartServices cartServices;
    private final RestClient.Builder builder;
    private final PaymentGateway paymentGateway;

    @Transactional
    public CheckoutResponse checkout(CheckoutRequest request) throws PaymentException{
        var cart = cartRepository.getCartsWithItems(request.getCartId()).orElse(null);
        if(cart == null){
            throw new CartNotFoundException();
        }
        if(cart.isEmpty()){
            throw new CartEmptyException();
        }
        Orders order = Orders.createOrder(cart, authService.getCurrentUser());
        ordersRepository.save(order);
        //create a checkout session
        //work flow... I made my own work flow...
        try{
            var session = paymentGateway.createCheckoutSession(order);

            cartServices.clearCart(cart.getId());

            return new CheckoutResponse(order.getId(), session.getCheckoutUrl());
        }
        catch(PaymentException e){
            System.out.println(e.getMessage());
            ordersRepository.delete(order);
            throw e;
        }
    }
}
