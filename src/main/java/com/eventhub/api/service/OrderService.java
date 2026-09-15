package com.eventhub.api.service;

import com.eventhub.api.dto.*;
import com.eventhub.api.entity.Order;
import com.eventhub.api.entity.OrderItem;
import com.eventhub.api.entity.ShippingAddress;
import com.eventhub.api.exception.InvalidSortFieldException;
import com.eventhub.api.exception.OrderNotFoundException;
import com.eventhub.api.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of(
            "createdAt",
            "totalAmount"
    );
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
                order.getCreatedAt(),
                order.getItems().stream()
                        .map(item -> new OrderItemResponseDto(
                                item.getProductId(),
                                item.getQuantity(),
                                item.getUnitPrice()
                        )).toList()
        );
    }

    /* ORJİNAL HALİ
    @Transactional
    public List<OrderResponseDto> getAllOrders() {
        return orderRepository.findAllWithItems()
                .stream()
                .map(order -> new OrderResponseDto(
                        order.getId(),
                        order.getCustomerId(),
                        order.getTotalAmount(),
                        order.getCurrency(),
                        order.getStatus(),
                        order.getCreatedAt(),
                        order.getItems().stream()
                                .map(item -> new OrderItemResponseDto(
                                        item.getProductId(),
                                        item.getQuantity(),
                                        item.getUnitPrice()
                                )).toList()
                )).toList();
    }

     */

    /* 2. VERİSYON
    @Transactional
    public PageResponseDto<OrderResponseDto> getAllOrders(Pageable pageable) {
        Page<OrderResponseDto> page = orderRepository.findAll(pageable)
                .map(order -> new OrderResponseDto(
                        order.getId(),
                        order.getCustomerId(),
                        order.getTotalAmount(),
                        order.getCurrency(),
                        order.getStatus(),
                        order.getCreatedAt(),
                        order.getItems().stream()
                                .map(item -> new OrderItemResponseDto(
                                        item.getProductId(),
                                        item.getQuantity(),
                                        item.getUnitPrice()
                                )).toList()
                ));
        return new PageResponseDto<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast()
        );
    }

     */

    @Transactional
    public PageResponseDto<OrderResponseDto> getAllOrders(Pageable pageable) {
        //Whitelist : Client istediği alanı değil, bizim izin verdiğimiz alanları sıralayabilsin.
        pageable.getSort().forEach(order -> {
            if (!ALLOWED_SORT_FIELDS.contains((order.getProperty()))) {
                throw new InvalidSortFieldException(order.getProperty());
            }
        });

        Pageable normalizedPageable = normalizeOrderPageable(pageable);

        Page<Order> orderPage = orderRepository.findAll(normalizedPageable);

        if (orderPage.isEmpty()) {
            return new PageResponseDto<>(
                    List.of(),
                    orderPage.getNumber(),
                    orderPage.getSize(),
                    orderPage.getTotalElements(),
                    orderPage.getTotalPages(),
                    orderPage.isFirst(),
                    orderPage.isLast()
            );
        }
        List<UUID> orderIds = orderPage.getContent()
                .stream()
                .map(Order::getId)
                .toList();

        Map<UUID, Order> ordersWithItems = orderRepository
                .findAllWithItemsByIdIn(orderIds)
                .stream()
                .collect(Collectors.toMap(
                        Order::getId,
                        Function.identity()
                ));
        List<OrderResponseDto> content = orderPage.getContent()
                .stream()
                .map(order -> {
                    Order orderWithItems = ordersWithItems.get(order.getId());
                    return new OrderResponseDto(
                            orderWithItems.getId(),
                            orderWithItems.getCustomerId(),
                            orderWithItems.getTotalAmount(),
                            orderWithItems.getCurrency(),
                            orderWithItems.getStatus(),
                            orderWithItems.getCreatedAt(),
                            orderWithItems.getItems().stream()
                                    .map(item -> new OrderItemResponseDto(
                                            item.getProductId(),
                                            item.getQuantity(),
                                            item.getUnitPrice()
                                    )).toList()
                    );

                }).toList();

        return new PageResponseDto<>(
                content,
                orderPage.getNumber(),
                orderPage.getSize(),
                orderPage.getTotalElements(),
                orderPage.getTotalPages(),
                orderPage.isFirst(),
                orderPage.isLast()
        );
    }

    private Pageable normalizeOrderPageable(Pageable pageable) {
        Sort sort = pageable.getSort();
        if (sort.isUnsorted()) {
            sort = Sort.by(Sort.Order.desc("createdAt"));
        }
        sort.forEach(order -> {
            if (!ALLOWED_SORT_FIELDS.contains(order.getProperty())) {
                throw new InvalidSortFieldException(order.getProperty());
            }
        });
        if (sort.getOrderFor("id") == null) {
            sort = sort.and(
                    Sort.by(Sort.Order.asc("id"))
            );
        }
        return PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                sort
        );
    }
}
