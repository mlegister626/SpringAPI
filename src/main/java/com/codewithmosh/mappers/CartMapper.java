package com.codewithmosh.mappers;

import com.codewithmosh.dtos.CartDto;
import com.codewithmosh.entities.entities.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CartMapper {
    CartDto toDto(Cart cart);
    Cart toEntity(CartDto dto);
    void update(CartDto dto, @MappingTarget Cart cart);
}


