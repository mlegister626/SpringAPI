package com.codewithmosh.entities;

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
    @OneToMany(mappedBy = "cartId", cascade = CascadeType.MERGE, fetch = FetchType.LAZY, orphanRemoval = true)
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
    public CartItems getItem(Long productId){
        return items.stream()
                .filter(item-> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);
    }
    public CartItems addItem(Product product){
        var cartItem = getItem(product.getId());
        if(cartItem!=null){
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        }else{
            cartItem = new CartItems();
            cartItem.setProduct(product);
            cartItem.setQuantity(1);
            cartItem.setCartId(this);
            items.add(cartItem);
        }
        return cartItem;
    }
    public void removeItem(Long productId){
        var cartItem = getItem(productId);
        if(cartItem!=null){
            items.remove(cartItem);
            cartItem.setCartId(null);
        }
    }
    public void clearCart(){
        items.clear();
    }
}
