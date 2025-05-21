package com.codewithmosh.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class CartItemsDto {
    private final Long productId;
    private final String productName;
    private final int quantity;
    private final BigDecimal price;
    private final BigDecimal totalPrice;

}
