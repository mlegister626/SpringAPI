package com.codewithmosh.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class ProductDTO {
    Long id;
    String name;
    String description;
    BigDecimal price;
    byte categoryId;
}
