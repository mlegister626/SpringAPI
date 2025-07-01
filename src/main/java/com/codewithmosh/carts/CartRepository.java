package com.codewithmosh.carts;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findById(UUID cartId);

    @EntityGraph(attributePaths = {"items.product"})
    @Query("select c from Cart c WHERE c.id = :cartId")
    Optional<Cart> getCartsWithItems(@Param("cartId") UUID cartId);
}
