package com.eventhub.api.Exception;

import java.util.UUID;

public class OrderNotFoundException extends EventHubException {


    private final UUID orderId;

    public OrderNotFoundException(UUID orderId) {
        super( "ORDER_NOT_FOUND",
                "Order with id '" + orderId + "' was not found");
        this.orderId = orderId;
    }

    public UUID getOrderId() {
        return orderId;
    }
}
