package com.eventhub.api.dto;

import com.eventhub.api.entity.OrderStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record OrderResponseDto(
        UUID orderId,
        UUID customerId,
        BigDecimal totalAmount,
        String currency,
        OrderStatus status,
        Instant createdAt,
        List<OrderItemResponseDto> items
) {
}
