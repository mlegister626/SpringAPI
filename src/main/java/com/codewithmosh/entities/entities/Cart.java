package com.codewithmosh.entities.entities;

import jakarta.persistence.*;
import lombok.*;

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
    @OneToMany(mappedBy = "cartId")
    private Set<CartItems> cartItems = new LinkedHashSet<>();

    @Column(name = "date",insertable = false,updatable = false)
    private Date date;

}
