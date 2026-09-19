package com.eventhub.outboxpublisher.service;

import com.eventhub.outboxpublisher.entity.OutboxEvent;
import com.eventhub.outboxpublisher.entity.OutboxStatus;
import com.eventhub.outboxpublisher.repository.OutboxEventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OutboxPublisherService {
    private final OutboxEventRepository outboxEventRepository;

    public OutboxPublisherService(OutboxEventRepository outboxEventRepository) {
        this.outboxEventRepository = outboxEventRepository;
    }

    @Transactional()
    public void claimPendingEvents() {
        List<OutboxEvent> events = outboxEventRepository.findByStatus(OutboxStatus.PENDING);

        for (OutboxEvent event : events) {
            int claimed = outboxEventRepository.claim(event.getId(),
                    OutboxStatus.PENDING,
                    OutboxStatus.PROCESSING);
            if (claimed == 1) {
                System.out.println(
                        "CLAIMED EVENT: " +
                                "eventId=" + event.getEventId() +
                                ", eventType=" + event.getEventType() +
                                ", aggregateId=" + event.getAggregateId() +
                                ", payload=" + event.getPayload()
                );

            }
        }
    }
}
