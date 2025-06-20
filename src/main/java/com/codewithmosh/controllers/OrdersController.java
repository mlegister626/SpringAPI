package com.codewithmosh.controllers;

import com.codewithmosh.dtos.OrdersDto;
import com.codewithmosh.entities.User;
import com.codewithmosh.mappers.OrdersMapper;
import com.codewithmosh.repositories.OrdersRepository;
import com.codewithmosh.repositories.UserRepository;
import com.codewithmosh.services.AuthService;
import com.codewithmosh.services.OrdersService;
import io.jsonwebtoken.lang.Maps;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrdersController {

    private final OrdersService OrdersService;

    @GetMapping("")
    public List<OrdersDto> getAllOrders(){
        return OrdersService.getOrdersDtos();
    }


}
