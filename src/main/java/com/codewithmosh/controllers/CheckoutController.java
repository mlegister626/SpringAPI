package com.codewithmosh.controllers;

import com.codewithmosh.dtos.CheckoutRequest;
import com.codewithmosh.dtos.CheckoutResponse;
import com.codewithmosh.dtos.ErrorDto;
import com.codewithmosh.exceptions.CartNotFoundException;
import com.codewithmosh.exceptions.PaymentException;
import com.codewithmosh.services.CheckoutService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.net.Webhook;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequiredArgsConstructor
@RequestMapping("/checkout")
public class CheckoutController {

    private final CheckoutService checkoutService;

    @Value("${stripe.webhookSecretKey}")
    private String webHookSecretKey;

    @PostMapping()
    public CheckoutResponse checkout(
           @Valid @RequestBody CheckoutRequest request) throws PaymentException {
        return checkoutService.checkout(request);
    }

    @PostMapping("webhook")
    public ResponseEntity<Void> handleWebHook(
            @RequestHeader("Stripe-Signature") String signature,
            @RequestBody String payload
    ){
        try {
            var event =  Webhook.constructEvent(payload,signature, webHookSecretKey);
            System.out.println(event.getType());
            var stripeObject = event.getDataObjectDeserializer().getObject().orElse(null);
            //charge -> (Charge) stripeObject
            // paymentIntentSuccess -> (PaymentIntent) stripeObject
            //everything in stripe can be casted onto a stripeObject
            switch (event.getType()){
                case "payment_intent.succeeded" -> {

                }
                case "payment_intent.failed" -> {

                }
            }
            return ResponseEntity.ok().build();
        } catch (SignatureVerificationException e) {
            return ResponseEntity.badRequest().build();
        }
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
