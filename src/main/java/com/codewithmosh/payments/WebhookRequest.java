package com.codewithmosh.payments;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.lang.NonNull;

import java.util.Map;
@Getter
@AllArgsConstructor
public class WebhookRequest {
    @NonNull()
    private Map<String, String> headers;
    private String payload;
}
