package com.codewithmosh.entities;


import com.codewithmosh.products.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "cartitems")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long Id;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cart_id")
    private Cart cartId;
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id" )
    private Product product;
    @Column(name = "quantity")
    private int quantity;

    public BigDecimal getTotalPrice(){
        return product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }
}
