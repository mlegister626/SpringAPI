package com.codewithmosh.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@Setter
public class ProductDTO {
    Long id;
    String name;
    String description;
    BigDecimal price;
    byte categoryId;
}
