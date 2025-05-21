package com.codewithmosh.repositories.repositories;

import com.codewithmosh.entities.entities.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemsRepository extends JpaRepository<CartItems, Long> {
}