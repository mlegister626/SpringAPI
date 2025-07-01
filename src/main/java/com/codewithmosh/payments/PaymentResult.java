package com.codewithmosh.payments;

import com.codewithmosh.orders.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PaymentResult {
    private Long orderId;
    private PaymentStatus status;
}
