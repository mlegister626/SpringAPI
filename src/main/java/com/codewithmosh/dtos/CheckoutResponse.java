package com.codewithmosh.dtos;

import lombok.Data;

@Data
public class CheckoutResponse {
    private long orderId;

    public CheckoutResponse(Long id) {
        this.orderId = id;
    }
}
