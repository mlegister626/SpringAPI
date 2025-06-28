package com.codewithmosh.mappers;

import com.codewithmosh.dtos.OrdersDto;
import com.codewithmosh.entities.Orders;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrdersMapper {
    @Mapping(target = "status",  expression = "java(order.getOrderStatus().name())")
    OrdersDto toDto(Orders order);
}
