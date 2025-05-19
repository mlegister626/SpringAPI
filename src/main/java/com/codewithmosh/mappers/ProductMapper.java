package com.codewithmosh.mappers;

import com.codewithmosh.dtos.ProductDTO;
import com.codewithmosh.entities.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "category.id", target = "categoryId")
    ProductDTO toDto(Product product);
}
