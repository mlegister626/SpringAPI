package com.codewithmosh.mappers;

import com.codewithmosh.dtos.OrdersDto;
import com.codewithmosh.entities.Orders;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrdersMapper {
    Orders toEntity(OrdersDto dto);
    OrdersDto toDto(Orders order);
}
