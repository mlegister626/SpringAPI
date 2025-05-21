package com.codewithmosh.repositories.repositories;

import com.codewithmosh.entities.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Object getCartsById(UUID id);

    Object findCartById(UUID id);
}
