package com.codewithmosh.carts;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(target = "totalPrice", expression = "java(cart.totalInCart())")
    CartDto toDto(Cart cart);
    @Mapping(target = "totalPrice", expression = "java(cartItems.getTotalPrice())")
    @Mapping(target = "cartProductDto", source = "product")
    CartItemsDto toCartItemsDto(CartItems cartItems);
    Cart toEntity(CartDto dto);

}


