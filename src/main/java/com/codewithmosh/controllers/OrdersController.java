package com.codewithmosh.controllers;

import com.codewithmosh.dtos.ErrorDto;
import com.codewithmosh.dtos.OrdersDto;
import com.codewithmosh.exceptions.OrderNotFoundException;
import com.codewithmosh.mappers.OrdersMapper;
import com.codewithmosh.repositories.OrdersRepository;
import com.codewithmosh.services.AuthService;
import com.codewithmosh.services.OrdersService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrdersController {

    private final OrdersService OrdersService;
    private final OrdersRepository ordersRepository;
    private final AuthService authService;
    private final OrdersMapper ordersMapper;

    @GetMapping("")
    public List<OrdersDto> getAllOrders(){
        return OrdersService.getOrdersDtos();
    }
    @GetMapping("/{id}")
    public OrdersDto getOneOrder(@PathVariable Long id){
        return OrdersService.getOneOrderDto(id);
    }
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorDto> handleOrderNotFound(){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorDto("The order has not been found, enter a valid OrderId"));
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity <ErrorDto> handleAccessDenied(){
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN).
                body(new ErrorDto("You are not authorized to access this order."));
    }

}
