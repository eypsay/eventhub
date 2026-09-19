package com.eventhub.api.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItemResponseDto(
        UUID productId,
        Integer quantity,
        BigDecimal unitPrice
) {
}
