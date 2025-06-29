package com.codewithmosh.entities;

import com.codewithmosh.services.AuthService;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;


    @Column(name = "status", length = 20)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_At")
    private LocalDateTime createdAt;

    @NotNull
    @Column(name = "total_price", precision = 10, scale = 2)
    private BigDecimal totalPrice;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Set<OrderItem> items = new LinkedHashSet<>();

    public static Orders createOrder(Cart cart, User customer) {
        Orders order = new Orders();
        order.setTotalPrice(cart.totalInCart());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setCustomer(customer);
        order.setCreatedAt(LocalDateTime.now());


        cart.getItems().forEach(item -> {
            var orderItem = new OrderItem(order, item.getProduct(), item.getQuantity());

            order.items.add(orderItem);
        });
        return order;
    }
    public boolean isPlacedByCustomer(User customer){
        return this.customer.equals(customer);
    }
}