package com.eventhub.api.event;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderCreatedEventPayload(
        UUID orderId,
        UUID customerId,
        BigDecimal totalAmount,
        String currency
) {
}
