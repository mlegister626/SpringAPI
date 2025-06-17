package com.codewithmosh.repositories;

import com.codewithmosh.entities.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemsRepository extends JpaRepository<CartItems, Long> {
}