package com.eventhub.api.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
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
    private Instant createdAt;
    @OneToMany(mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    protected Order() {
    }

    public Order(UUID customerId, String currency, PaymentMethod paymentMethod, ShippingAddress shippingAddress) {
        this.customerId = customerId;
        this.currency = currency;
        this.paymentMethod = paymentMethod;
        this.shippingAddress = shippingAddress;
        this.status = OrderStatus.CREATED;
        this.totalAmount = BigDecimal.ZERO;
        this.createdAt = Instant.now();
    }
    public List<OrderItem> getItems(){
        return Collections.unmodifiableList(items);
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void addItem(OrderItem item) {
        items.add(item);
        item.setOrder(this);

        BigDecimal itemTotal = item.getUnitPrice()
                .multiply(BigDecimal.valueOf(item.getQuantity()));
        this.totalAmount = this.totalAmount.add(itemTotal);
    }

    public void removeItem(OrderItem item) {
        if (items.remove(item)) {
            BigDecimal itemTotal = item.getUnitPrice()
                    .multiply(BigDecimal.valueOf(item.getQuantity()));
            this.totalAmount = this.totalAmount.subtract(itemTotal);

            item.setOrder(null);
        }
    }

}
