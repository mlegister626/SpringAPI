package com.codewithmosh.dtos;

import com.codewithmosh.entities.OrderItem;
import com.codewithmosh.entities.OrderStatus;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@Data
@AllArgsConstructor
public class OrdersDto {

    private long id;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private List<OrderItemDto> items;
    private BigDecimal totalPrice = BigDecimal.ZERO;

}
