package com.eventhub.api.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {
    //System.out.println("ORDER:OrderStatus: " + order.getStatus());
    //System.out.println("ORDER:OrderStatus markAsPaid: " + order.getStatus());
    @Test
    void shouldMarkCreatedOrderAsPaid() {
        Order order = new Order(
                UUID.randomUUID(),
                "TRY",
                PaymentMethod.CREDIT_CARD,
                new ShippingAddress(
                        "eyp say",
                        "Ataturk mh",
                        "Gaziantep",
                        "27000",
                        "TR"
                )
        );
        assertEquals(OrderStatus.CREATED, order.getStatus());
        System.out.println("shouldMarkCreatedOrderAsPaid:ORDER:OrderStatus: " + order.getStatus());
        order.markAsPaid();
        System.out.println("shouldMarkCreatedOrderAsPaidORDER:OrderStatus markAsPaid: " + order.getStatus() + "\n");
        assertEquals(OrderStatus.PAID, order.getStatus());

    }

    @Test
    public void shouldNotMarkPaidOrderAsPaidAgain() {
        Order order = new Order(
                UUID.randomUUID(),
                "TRY",
                PaymentMethod.CREDIT_CARD,
                new ShippingAddress(
                        "eyp say",
                        "Ataturk mh",
                        "Gaziantep",
                        "27000",
                        "TR"
                )
        );

        order.markAsPaid();
        System.out.println("shouldNotMarkPaidOrderAsPaidAgain:Status before second markAsPaid: " + order.getStatus() + "\n");
        assertThrows(
                IllegalStateException.class, order::markAsPaid
        );
    }

    @Test
    public void shouldCancelCreatedOrder() {
        Order order = new Order(
                UUID.randomUUID(),
                "TRY",
                PaymentMethod.CREDIT_CARD,
                new ShippingAddress(
                        "eyp say",
                        "Ataturk mh",
                        "Gaziantep",
                        "27000",
                        "TR"
                )
        );
        System.out.println("shouldCancelCreatedOrder:ORDER:OrderStatus: " + order.getStatus());
        order.cancel();
        System.out.println("shouldCancelCreatedOrder:ORDER:OrderStatus markAsPaid: " + order.getStatus() + "\n");
        assertEquals(OrderStatus.CANCELLED, order.getStatus());
    }

    @Test
    public void shouldCancelPaidOrder() {
        Order order = new Order(
                UUID.randomUUID(),
                "TRY",
                PaymentMethod.CREDIT_CARD,
                new ShippingAddress(
                        "eyp say",
                        "Ataturk mh",
                        "Gaziantep",
                        "27000",
                        "TR"
                )
        );
        System.out.println("shouldCancelPaidOrder:ORDER:OrderStatus: " + order.getStatus());
        order.markAsPaid();
        System.out.println("shouldCancelPaidOrder:ORDER:OrderStatus markAsPaid: " + order.getStatus());
        order.cancel();
        System.out.println("shouldCancelPaid:OrderORDER:OrderStatus cancelled: " + order.getStatus() + "\n");
        assertEquals(OrderStatus.CANCELLED, order.getStatus());
    }

    @Test
    public void shouldNotMarkCancelledOrderAsPaid() {
        Order order = new Order(
                UUID.randomUUID(),
                "TRY",
                PaymentMethod.CREDIT_CARD,
                new ShippingAddress(
                        "eyp say",
                        "Ataturk mh",
                        "Gaziantep",
                        "27000",
                        "TR"
                )
        );
        System.out.println("shouldNotMarkCancelledOrderAsPaid:ORDER:OrderStatus: " + order.getStatus());
        order.cancel();
        System.out.println("shouldNotMarkCancelledOrderAsPaid:OrderORDER:OrderStatus cancelled: " + order.getStatus());

        assertThrows(
                IllegalStateException.class,
                order::markAsPaid);
        System.out.println("shouldNotMarkCancelledOrderAsPaid:ORDER:OrderStatus markAsPaid: " + order.getStatus() + "\n");
    }

    @Test
    public void shouldNotCancelAlreadyCancelledOrder() {
        Order order = new Order(
                UUID.randomUUID(),
                "TRY",
                PaymentMethod.CREDIT_CARD,
                new ShippingAddress(
                        "eyp say",
                        "Ataturk mh",
                        "Gaziantep",
                        "27000",
                        "TR"
                )
        );
        System.out.println("shouldNotCancelAlreadyCancelledOrder:ORDER:OrderStatus: " + order.getStatus());
        order.cancel();
        System.out.println("shouldNotCancelAlreadyCancelledOrder:OrderORDER:OrderStatus cancelled: " + order.getStatus());

        assertThrows(
                IllegalStateException.class,
                order::cancel);
        System.out.println("shouldNotCancelAlreadyCancelledOrder:ORDER:OrderStatus cancelled: " + order.getStatus() + "\n");
    }

    @Test
    public void shouldCalculateTotalAmountWhenItemAdded() {
        Order order = new Order(
                UUID.randomUUID(),
                "TRY",
                PaymentMethod.CREDIT_CARD,
                new ShippingAddress(
                        "eyp say",
                        "Ataturk mh",
                        "Gaziantep",
                        "27000",
                        "TR"
                )
        );
        OrderItem item = new OrderItem(
                UUID.randomUUID(),
                2,
                new BigDecimal("125.50")
        );
        order.addItem(item);
        assertEquals(
                new BigDecimal("251.00"),
                order.getTotalAmount());
    }

    @Test
    public void shouldCalculateTotalAmountForMultipleItems() {
        Order order = new Order(
                UUID.randomUUID(),
                "TRY",
                PaymentMethod.CREDIT_CARD,
                new ShippingAddress(
                        "eyp say",
                        "Ataturk mh",
                        "Gaziantep",
                        "27000",
                        "TR"
                )
        );
        OrderItem firstItem = new OrderItem(
                UUID.randomUUID(),
                2,
                new BigDecimal("125.50")
        );
        OrderItem secondItem = new OrderItem(
                UUID.randomUUID(),
                3,
                new BigDecimal("50.00")
        );
        order.addItem(firstItem);
        order.addItem(secondItem);
        assertEquals(
                new BigDecimal("401.00"),
                order.getTotalAmount());
    }

    @Test
    public void shouldRecalculateTotalAmountWhenItemRemoved() {
        Order order = new Order(
                UUID.randomUUID(),
                "TRY",
                PaymentMethod.CREDIT_CARD,
                new ShippingAddress(
                        "eyp say",
                        "Ataturk mh",
                        "Gaziantep",
                        "27000",
                        "TR"
                )
        );
        OrderItem firstItem = new OrderItem(
                UUID.randomUUID(),
                2,
                new BigDecimal("125.50")
        );
        OrderItem secondItem = new OrderItem(
                UUID.randomUUID(),
                3,
                new BigDecimal("50.00")
        );
        order.addItem(firstItem);
        order.addItem(secondItem);
        assertEquals(
                new BigDecimal("401.00"),
                order.getTotalAmount());

        order.removeItem(firstItem);
        assertEquals(
                new BigDecimal("150.00"),
                order.getTotalAmount());
    }

    @Test
    void shouldRemoveItemFromOrderItems() {
        Order order = new Order(
                UUID.randomUUID(),
                "TRY",
                PaymentMethod.CREDIT_CARD,
                new ShippingAddress(
                        "eyp say",
                        "Ataturk mh",
                        "Gaziantep",
                        "27000",
                        "TR"
                )
        );
        OrderItem item = new OrderItem(
                UUID.randomUUID(),
                2,
                new BigDecimal("125.50")
        );
        order.addItem(item);
        assertEquals(1, order.getItems().size());
        assertEquals(item, order.getItems().get(0));
        order.removeItem(item);
        assertEquals(0, order.getItems().size());
    }

    @Test
    void shouldClearOrderReferenceWhenItemRemoved() {
        Order order = new Order(
                UUID.randomUUID(),
                "TRY",
                PaymentMethod.CREDIT_CARD,
                new ShippingAddress(
                        "eyp say",
                        "Ataturk mh",
                        "Gaziantep",
                        "27000",
                        "TR"
                )
        );
        OrderItem item = new OrderItem(
                UUID.randomUUID(),
                2,
                new BigDecimal("125.50")
        );
        order.addItem(item);
        assertEquals(order, item.getOrder());
        order.removeItem(item);
        assertNull(item.getOrder());
    }

    @Test
    void shouldDoNothingWhenRemovingItemThatWasNeverAdded() {

        Order order = new Order(
                UUID.randomUUID(),
                "TRY",
                PaymentMethod.CREDIT_CARD,
                new ShippingAddress(
                        "eyp say",
                        "Ataturk mh",
                        "Gaziantep",
                        "27000",
                        "TR"
                )
        );
        OrderItem item = new OrderItem(
                UUID.randomUUID(),
                2,
                new BigDecimal("125.50")
        );
        order.removeItem(item);
        assertEquals(0, order.getItems().size());
        assertEquals(BigDecimal.ZERO, order.getTotalAmount());
        assertNull(item.getOrder());
    }

    @Test
    void shouldNotAllowSameItemToBeAddedTwice() {
        Order order = new Order(
                UUID.randomUUID(),
                "TRY",
                PaymentMethod.CREDIT_CARD,
                new ShippingAddress(
                        "eyp say",
                        "Ataturk mh",
                        "Gaziantep",
                        "27000",
                        "TR"
                )
        );
        OrderItem item = new OrderItem(
                UUID.randomUUID(),
                2,
                new BigDecimal("125.50")
        );
        order.addItem(item);
        assertThrows(
                IllegalArgumentException.class,
                () -> order.addItem(item)
        );
    }

    @Test
    void shouldNotAllowItemFromAnotherOrder() {
        Order order1 = new Order(
                UUID.randomUUID(),
                "TRY",
                PaymentMethod.CREDIT_CARD,
                new ShippingAddress(
                        "eyp say",
                        "Ataturk mh",
                        "Gaziantep",
                        "27000",
                        "TR"
                )
        );
        Order order2 = new Order(
                UUID.randomUUID(),
                "TRY",
                PaymentMethod.CREDIT_CARD,
                new ShippingAddress(
                        "eyp say",
                        "Ataturk mh",
                        "Gaziantep",
                        "27000",
                        "TR"
                )
        );
        OrderItem item = new OrderItem(
                UUID.randomUUID(),
                2,
                new BigDecimal("125.50")
        );

        order1.addItem(item);
        assertThrows(
                IllegalArgumentException.class,
                () -> order2.addItem(item)
        );
    }
}
