package com.codewithmosh.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class CartDto {
    private UUID id;
    private List<CartItemsDto> items = new ArrayList<>();
    private BigDecimal totalPrice = BigDecimal.ZERO;
}
