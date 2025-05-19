package com.codewithmosh.controllers.controllers;

import com.codewithmosh.dtos.ProductDTO;
import com.codewithmosh.entities.entities.Product;
import com.codewithmosh.mappers.ProductMapper;
import com.codewithmosh.repositories.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.annotations.Parameter;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {
    private ProductRepository productRepository;
    private ProductMapper productMapper;
    @GetMapping()
    public List<ProductDTO> getAllProducts(@RequestParam(name = "categoryId", required = false)Byte category){
        List<Product> product;
        if(category != null){
            product = productRepository.findByCategoryId(category);
        }else{
            product = productRepository.findAllWithCategory();
        }
       return product
                .stream()
                .map(productMapper::toDto)
                .toList();
    }
    @GetMapping("{categoryId}")
    public ResponseEntity<ProductDTO> getProductByCategoryId(@PathVariable byte id ){
        var product = productRepository.findByCategoryId(id).stream().findFirst().orElse(null);
        if(product == null){
            return ResponseEntity.notFound().build();
        }
        var productDto = new ProductDTO(product.getId(),product.getName(), product.getDescription(), product.getPrice(), product.getCategory().getId());
        return ResponseEntity.ok(productMapper.toDto(product));
    }
}
