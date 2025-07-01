package com.codewithmosh.payments;

import com.codewithmosh.dtos.ErrorDto;
import com.codewithmosh.carts.CartNotFoundException;
import com.codewithmosh.orders.OrdersRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/checkout")
public class CheckoutController {

    private final CheckoutService checkoutService;
    private final OrdersRepository ordersRepository;

    @Value("${stripe.webhookSecretKey}")
    private String webHookSecretKey;

    @PostMapping()
    public CheckoutResponse checkout(
           @Valid @RequestBody CheckoutRequest request) throws PaymentException {
        return checkoutService.checkout(request);
    }

    @PostMapping("webhook")
    public void handleWebHook(
            @RequestHeader Map<String,String> signature,
            @RequestBody String payload
    ){
        checkoutService.handleWebhookEvent(new WebhookRequest(signature, payload));
    }

    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<?> handlePaymentException(){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDto("Error Creating a checkout session."));
    }

    @ExceptionHandler({CartNotFoundException.class, CartNotFoundException.class})
    public ResponseEntity<ErrorDto> handleCartNotFound(CartNotFoundException ex){
        return ResponseEntity.badRequest().body(new ErrorDto(ex.getMessage()));
    }
}
