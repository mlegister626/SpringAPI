package com.codewithmosh.controllers.controllers;

import com.codewithmosh.dtos.ProductDTO;
import com.codewithmosh.entities.entities.Product;
import com.codewithmosh.mappers.ProductMapper;
import com.codewithmosh.repositories.repositories.CategoryRepository;
import com.codewithmosh.repositories.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {
    private final CategoryRepository categoryRepository;
    private  ProductRepository productRepository;
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
    @PostMapping("")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO request,
                                                    UriComponentsBuilder uriBuilder){

            var category = categoryRepository.findById(request.getCategoryId()).orElse(null);
            if(category == null){
                return ResponseEntity.badRequest().build();
            }
            var product = productMapper.toEntity(request);
            product.setCategory(category);
            productRepository.save(product);
            request.setId(product.getId());
            var uri = uriBuilder.path("/products/{id}").buildAndExpand(request.getId()).toUri();

            return ResponseEntity.created(uri).body(request);
    }
    @PutMapping("{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable long id, @RequestBody ProductDTO request){
        var category = categoryRepository.findById(request.getCategoryId()).orElse(null);
        if(category == null){
            return ResponseEntity.notFound().build();
        }
        var product = productRepository.findById(id).orElse(null);
        if(product == null){
            return ResponseEntity.notFound().build();
        }
        productMapper.update(request,product);
        productRepository.save(product);
        request.setId(product.getId());

        return ResponseEntity.ok(request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable long id){
        var product = productRepository.findById(id).orElse(null);
        if(product == null){
            return ResponseEntity.notFound().build();
        }
        productRepository.delete(product);
        return ResponseEntity.noContent().build();
    }


}
