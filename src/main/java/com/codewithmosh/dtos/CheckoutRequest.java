package com.codewithmosh.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@AllArgsConstructor
@Data
public class CheckoutRequest {
    @NotNull(message = "The cartId is null")
    private UUID cartId;
}
