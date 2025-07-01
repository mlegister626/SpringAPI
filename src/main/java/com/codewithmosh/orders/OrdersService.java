package com.codewithmosh.orders;

import com.codewithmosh.auth.AuthService;
import com.codewithmosh.users.User;
import com.codewithmosh.users.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrdersService {

    private final AuthService authService;
    private final OrdersRepository ordersRepository;
    private final OrdersMapper ordersMapper;
    private final UserMapper userMapper;

    public List<OrdersDto> getOrdersDtos() {
        User user = authService.getCurrentUser();

        var orders = ordersRepository.getOrdersByCustomer(user);

        return orders.stream().map(ordersMapper::toDto).toList();
    }

    public OrdersDto getOneOrderDto(Long id){
        var order = ordersRepository.getOrderWithItems(id)
                .orElseThrow(OrderNotFoundException::new);
        if(!order.isPlacedByCustomer(authService.getCurrentUser())){
            throw new AccessDeniedException("You don't have access to this order");
        }
        return ordersMapper.toDto(order);
    }
}

