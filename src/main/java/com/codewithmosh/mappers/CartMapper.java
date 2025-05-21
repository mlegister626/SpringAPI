package com.codewithmosh.mappers;

import com.codewithmosh.dtos.CartDto;
import com.codewithmosh.dtos.CartItemsDto;
import com.codewithmosh.entities.entities.Cart;
import com.codewithmosh.entities.entities.CartItems;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(target = "totalPrice", expression = "java(cart.totalInCart())")
    CartDto toDto(Cart cart);
    @Mapping(target = "totalPrice", expression = "java(cartItems.getTotalPrice())")
    CartItemsDto toCartItemsDto(CartItems cartItems);
    Cart toEntity(CartDto dto);
    void update(CartDto dto, @MappingTarget Cart cart);
}


