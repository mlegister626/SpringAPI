package com.codewithmosh.mappers;

import com.codewithmosh.dtos.ProductDTO;
import com.codewithmosh.entities.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "category.id", target = "categoryId")
    ProductDTO toDto(Product product);
    @Mapping(target = "category", ignore = true)
    Product toEntity(ProductDTO request);
    @Mapping(target = "id", ignore = true)
    void update(ProductDTO dto, @MappingTarget Product product);
}
