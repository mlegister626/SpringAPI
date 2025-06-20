package com.codewithmosh.repositories;

import com.codewithmosh.entities.Orders;
import com.codewithmosh.entities.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
   Optional<Orders> findOrdersByCustomerId(Long userId);

   @EntityGraph(attributePaths = {"customer"})
   @Query("select o from Orders o where o.customer = :customer")
   List<Orders> getAllByCustomer(User customer);
}