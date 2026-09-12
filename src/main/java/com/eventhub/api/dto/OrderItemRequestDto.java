package com.eventhub.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItemRequestDto(
        @NotNull
        UUID productId,
        @NotNull
        @Positive
        Integer quantity,
        @NotNull
        @Positive
        BigDecimal unitPrice
) {
}
