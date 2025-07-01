package com.codewithmosh.carts;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemsRepository extends JpaRepository<CartItems, Long> {
}