package com.codewithmosh.entities.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "cart")
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;
    //need separate id and a separate data structure to map these and keep together the data.
    @OneToMany(mappedBy = "cartId", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private Set<CartItems> items = new LinkedHashSet<>();

    @Column(name = "date",insertable = false,updatable = false)
    private Date date;

    public BigDecimal totalInCart(){
        BigDecimal total = new BigDecimal(0);
        for (CartItems item : items) {
            total = total.add(item.getTotalPrice());
        }
        return total;
    }

}
