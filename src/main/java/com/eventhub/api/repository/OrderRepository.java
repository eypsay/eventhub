package com.eventhub.api.repository;

import com.eventhub.api.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    @Query("""
            select distinct o
            from Order o 
            left join fetch o.items
            """)
    List<Order> findAllWithItems();

    @Query("""
            select distinct o
            from Order o
            left join fetch o.items
            where o.id in :orderIds
            """)
    List<Order> findAllWithItemsByIdIn(@Param("orderIds") List<UUID> orderIds);
}
