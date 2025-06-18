package com.codewithmosh.mappers;

import com.codewithmosh.dtos.OrdersDto;
import com.codewithmosh.entities.Orders;

public interface OrdersMapper {
    OrdersDto toDto(Orders order);
}
