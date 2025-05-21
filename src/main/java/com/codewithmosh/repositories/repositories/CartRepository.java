package com.codewithmosh.repositories.repositories;

import com.codewithmosh.entities.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findById(UUID cartId);
}
