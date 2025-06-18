package com.codewithmosh.dtos;

import com.codewithmosh.entities.OrderItem;
import com.codewithmosh.entities.OrderStatus;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;

@Data
@AllArgsConstructor
public class OrdersDto {

    private long id;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private HashSet<OrderItem> items = new HashSet<>();
    private BigDecimal totalPrice = BigDecimal.ZERO;

}
