package com.codewithmosh.payments;

import com.codewithmosh.entities.Orders;

import java.util.Optional;

public interface PaymentGateway {
    CheckoutSession createCheckoutSession(Orders order);
    Optional<PaymentResult> parseWebhookRequest(WebhookRequest request);
}
