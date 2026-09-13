package com.eventhub.api.service;

import com.eventhub.api.dto.OrderCreateRequestDto;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    public String createOrder(OrderCreateRequestDto order) {
        return "ORDER CREATED! " + order.customerId();
    }
}
