package com.codewithmosh.entities;

import com.codewithmosh.products.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders order;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    @ColumnDefault("0")
    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "total_price", precision = 10, scale = 2)
    private BigDecimal totalPrice;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    public OrderItem(Orders order, Product product, int quantity) {
        this.order = order;
        this.unitPrice = product.getPrice();
        this.quantity = quantity;
        this.totalPrice = unitPrice.multiply(BigDecimal.valueOf(quantity));
        this.product = product;
    }
}