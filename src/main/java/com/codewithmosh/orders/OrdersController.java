package com.codewithmosh.orders;

import com.codewithmosh.common.ErrorDto;
import com.codewithmosh.auth.AuthService;
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
