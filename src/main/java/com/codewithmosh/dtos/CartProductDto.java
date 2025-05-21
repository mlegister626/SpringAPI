package com.codewithmosh.dtos;

import lombok.Data;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
@Data

public class CartProductDto {
    private long id;
    private String name;
    private BigDecimal price;
}
