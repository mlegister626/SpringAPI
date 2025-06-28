package com.codewithmosh.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data

public class OrderProductDto {
    private Long id;
    private String name;
    private BigDecimal price;
}
