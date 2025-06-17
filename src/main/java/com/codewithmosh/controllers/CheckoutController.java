package com.codewithmosh.controllers;

import com.codewithmosh.dtos.CheckoutRequest;
import com.codewithmosh.dtos.CheckoutResponse;
import com.codewithmosh.entities.OrderItem;
import com.codewithmosh.entities.OrderStatus;
import com.codewithmosh.entities.Orders;
import com.codewithmosh.repositories.CartRepository;
import com.codewithmosh.repositories.OrdersRepository;
import com.codewithmosh.services.AuthService;
import com.codewithmosh.services.CartServices;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/checkout")
public class CheckoutController {

    private final CartRepository cartRepository;
    private final AuthService authService;
    private final OrdersRepository ordersRepository;
    private final CartServices cartServices;


    @PostMapping()
    public ResponseEntity<?> checkout(@Valid @RequestBody CheckoutRequest requestBody){
        var cart = cartRepository.getCartsWithItems(requestBody.getCartId()).orElse(null);
        if (cart == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Cart not found."));
        }
        if(cart.getItems().isEmpty()){
            return ResponseEntity.badRequest().body(Map.of("error", "Cart is empty."));
        }

        Orders order = new Orders();
        order.setTotalPrice(cart.totalInCart());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setCustomer(authService.getCurrentUser());

        cart.getItems().forEach(item -> {
            var orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProductId(item.getId());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setTotalPrice(item.getTotalPrice());
            orderItem.setUnitPrice(item.getProduct().getPrice());
            order.getItems().add(orderItem);
        });
    ordersRepository.save(order);

    cartServices.clearCart(cart.getId());

    return ResponseEntity.ok(new CheckoutResponse(order.getId()));

    }
}
