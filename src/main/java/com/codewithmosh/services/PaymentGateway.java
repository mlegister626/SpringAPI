package com.codewithmosh.services;

import com.codewithmosh.entities.Orders;

public interface PaymentGateway {
    CheckoutSession createCheckoutSession(Orders order);
}
