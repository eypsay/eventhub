package com.eventhub.api.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;
    private UUID productId;
    private Integer quantity;
    private BigDecimal unitPrice;
    @ManyToOne
    @JoinColumn(name="order_id",nullable = false)
    private Order order;

    protected OrderItem() {
    }
}
