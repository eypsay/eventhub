package com.eventhub.api.controller;

import com.eventhub.api.dto.OrderCreateRequestDto;
import com.eventhub.api.dto.OrderCreateResponseDto;
import com.eventhub.api.dto.OrderResponseDto;
import com.eventhub.api.dto.PageResponseDto;
import com.eventhub.api.exception.OrderNotFoundException;
import com.eventhub.api.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")

public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/orders")
    public ResponseEntity<OrderCreateResponseDto> createOrder(@Valid @RequestBody OrderCreateRequestDto order) {
        OrderCreateResponseDto response = orderService.createOrder(order);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);


    }

    @PostMapping("/eyp")
    public String errorTest(@Valid @RequestBody OrderCreateRequestDto order) {
        throw new OrderNotFoundException(order.customerId());

    }

    @GetMapping("/orders/{orderId}")
    public OrderResponseDto getOrder(@PathVariable UUID orderId) {
        return orderService.getOrderResponse(orderId);
    }

    @GetMapping("/orders")
    public PageResponseDto<OrderResponseDto> getAllOrders(Pageable pageable) {
        return orderService.getAllOrders(pageable);
    }
}
