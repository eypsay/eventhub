package com.eventhub.api.entity;

import com.eventhub.api.dto.PaymentMethod;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;
    private UUID customerId;
    private BigDecimal totalAmount;
    private String currency;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    @Embedded
    private ShippingAddress shippingAddress;
    @OneToMany(mappedBy = "order")
    private List<OrderItem> items= new ArrayList<>();
    protected Order() {
    }

}
