package com.codewithmosh.dtos;

import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.Mapping;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class CartItemsDto {

    private CartProductDto cartProductDto;
    private BigDecimal totalPrice;
    private int quantity;
}
