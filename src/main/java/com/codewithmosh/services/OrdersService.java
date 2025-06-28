package com.codewithmosh.services;

import com.codewithmosh.dtos.OrdersDto;
import com.codewithmosh.entities.User;
import com.codewithmosh.mappers.OrdersMapper;
import com.codewithmosh.repositories.OrdersRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class OrdersService {

    private final AuthService authService;
    private final OrdersRepository ordersRepository;
    private final OrdersMapper ordersMapper;

    public List<OrdersDto> getOrdersDtos() {
        User user = authService.getCurrentUser();

        var orders = ordersRepository.getAllByCustomer(user);

        return orders.stream().map(ordersMapper::toDto).toList();
    }}
