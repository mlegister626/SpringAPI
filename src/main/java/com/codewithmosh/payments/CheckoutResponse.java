package com.codewithmosh.payments;

import lombok.Data;

@Data
public class CheckoutResponse {
    private long orderId;
    private String checkoutUrl;

    public CheckoutResponse(Long id, String url) {
        this.orderId = id;
        this.checkoutUrl = url;
    }
}
