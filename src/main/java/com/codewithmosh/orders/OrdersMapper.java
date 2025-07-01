package com.codewithmosh.orders;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrdersMapper {
    @Mapping(target = "status",  expression = "java(order.getOrderStatus().name())")
    OrdersDto toDto(Orders order);
}
