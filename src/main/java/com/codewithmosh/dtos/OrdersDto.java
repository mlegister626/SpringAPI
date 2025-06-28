package com.codewithmosh.dtos;

import com.codewithmosh.entities.OrderItem;
import com.codewithmosh.entities.OrderStatus;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Data
@AllArgsConstructor
public class OrdersDto {

    private Long id;
    private String status;
    private LocalDateTime createdAt;
    private List<OrderItemDto> items = new ArrayList<>();
    private BigDecimal totalPrice = BigDecimal.ZERO;

}
