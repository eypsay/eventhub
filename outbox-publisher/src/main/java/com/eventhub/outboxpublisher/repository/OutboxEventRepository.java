package com.eventhub.outboxpublisher.repository;

import com.eventhub.outboxpublisher.entity.OutboxEvent;
import com.eventhub.outboxpublisher.entity.OutboxStatus;
import org.hibernate.sql.ast.tree.expression.JdbcParameter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OutboxEventRepository extends JpaRepository<OutboxEvent, UUID> {
    List<OutboxEvent> findByStatus(OutboxStatus status);
}
