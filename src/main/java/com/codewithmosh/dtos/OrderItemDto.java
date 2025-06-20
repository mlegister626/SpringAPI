package com.codewithmosh.dtos;

import lombok.Data;

@Data
public class OrderItemDto {
   private OrderProductDto productOrder;
   private int quantity;
   private double totalPrice;
}
