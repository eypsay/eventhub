package com.eventhub.api.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "outbox_event")
public class OutboxEvent {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;
    @Column(nullable = false, unique = true)
    private UUID eventId;
    @Column(nullable = false)
    private String eventType;
    @Column(nullable = false)
    private String aggregateType;
    @Column(nullable = false)
    private UUID aggregateId;

    @Column(nullable = false, columnDefinition = "text")
    private String payload;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OutboxStatus status;
    @Column(nullable = false)
    private Instant createdAt;

    private Instant publishedAt;
    @Column(nullable = false)
    private int retryCount;

    protected OutboxEvent() {
    }

    public OutboxEvent(UUID eventId, String eventType, String aggregateType, UUID aggregateId, String payload) {
        this.id=null;
        this.eventId = eventId;
        this.eventType = eventType;
        this.aggregateType = aggregateType;
        this.aggregateId = aggregateId;
        this.payload = payload;
        this.status=OutboxStatus.PENDING;
        this.createdAt=Instant.now();
        this.retryCount=0;
    }

    public UUID getId() {
        return id;
    }

    public UUID getEventId() {
        return eventId;
    }

    public String getEventType() {
        return eventType;
    }

    public String getAggregateType() {
        return aggregateType;
    }

    public UUID getAggregateId() {
        return aggregateId;
    }

    public String getPayload() {
        return payload;
    }

    public OutboxStatus getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getPublishedAt() {
        return publishedAt;
    }

    public int getRetryCount() {
        return retryCount;
    }
}
