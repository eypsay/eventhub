package com.eventhub.api.controller;

import com.eventhub.api.exception.OrderNotFoundException;
import com.eventhub.api.dto.OrderCreateRequestDto;
import com.eventhub.api.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")

public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/orders")
    public String createOrder(@Valid @RequestBody OrderCreateRequestDto order){
       return orderService.createOrder(order);

    }
    @PostMapping("/eyp")
    public String errorTest(@Valid @RequestBody OrderCreateRequestDto order){
        throw new OrderNotFoundException(order.customerId());

    }
}
