package com.eventhub.outboxpublisher.repository;

import com.eventhub.outboxpublisher.entity.OutboxEvent;
import com.eventhub.outboxpublisher.entity.OutboxStatus;
import org.hibernate.sql.ast.tree.expression.JdbcParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface OutboxEventRepository extends JpaRepository<OutboxEvent, UUID> {
    List<OutboxEvent> findByStatus(OutboxStatus status);

    @Modifying
    @Query("""
            update OutboxEvent e
                          set e.status = :processing
                        where e.id = :id
                          and e.status = :pending
            """)
    int claim(
            @Param("id") UUID id,
            @Param("pending") OutboxStatus pending,
            @Param("processing") OutboxStatus processing
    );
}
