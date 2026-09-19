package com.eventhub.outboxpublisher.scheduler;

import com.eventhub.outboxpublisher.service.OutboxPublisherService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OutboxPublisherScheduler {
    private final OutboxPublisherService outboxPublisherService;

    public OutboxPublisherScheduler(OutboxPublisherService outboxPublisherService) {
        this.outboxPublisherService = outboxPublisherService;
    }

    @Scheduled(fixedDelay = 5000)
    public void publishPendingEvents() {
        outboxPublisherService.publishPendingEvents();

    }
}
