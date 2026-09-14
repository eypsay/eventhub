package com.eventhub.api.service;

import com.eventhub.api.dto.OrderCreateRequestDto;
import com.eventhub.api.dto.OrderCreateResponseDto;
import com.eventhub.api.dto.OrderItemRequestDto;
import com.eventhub.api.dto.OrderResponseDto;
import com.eventhub.api.entity.Order;
import com.eventhub.api.entity.OrderItem;
import com.eventhub.api.entity.ShippingAddress;
import com.eventhub.api.exception.OrderNotFoundException;
import com.eventhub.api.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public OrderCreateResponseDto createOrder(OrderCreateRequestDto orderCreateRequest) {
        ShippingAddress shippingAddress = new ShippingAddress(
                orderCreateRequest.shippingAddress().recipientName(),
                orderCreateRequest.shippingAddress().addressLine(),
                orderCreateRequest.shippingAddress().city(),
                orderCreateRequest.shippingAddress().postalCode(),
                orderCreateRequest.shippingAddress().country()
        );

        Order order = new Order(
                orderCreateRequest.customerId(),
                orderCreateRequest.currency(),
                orderCreateRequest.paymentMethod(),
                shippingAddress
        );

        for (OrderItemRequestDto itemRequest : orderCreateRequest.items()) {
            OrderItem item = new OrderItem(
                    itemRequest.productId(),
                    itemRequest.quantity(),
                    itemRequest.unitPrice()
            );
            order.addItem(item);
        }
        orderRepository.save(order);


        return new OrderCreateResponseDto(
                order.getId(),
                order.getStatus(),
                order.getTotalAmount(),
                order.getCurrency()
        );
    }

    @Transactional(readOnly = true)
    public OrderResponseDto getOrderResponse(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
        return new OrderResponseDto(
                order.getId(),
                order.getCustomerId(),
                order.getTotalAmount(),
                order.getCurrency(),
                order.getStatus(),
                order.getCreatedAt()
        );
    }
}
