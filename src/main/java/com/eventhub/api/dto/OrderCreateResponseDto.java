package com.eventhub.api.dto;

import com.eventhub.api.entity.OrderStatus;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderCreateResponseDto(
        UUID orderId,
        OrderStatus status,
        BigDecimal totalAmount,
        String currency
) {
}
