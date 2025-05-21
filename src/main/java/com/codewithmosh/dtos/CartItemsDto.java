package com.codewithmosh.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class CartItemsDto {
    private CartProductDto cartProductDto;
    private BigDecimal totalPrice;
    private int quantity;
}
