package com.uniac.book_teste_software;

import com.uniac.book_teste_software.model.*;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {

    @Test
    void testBasicFields() {
        Order order = new Order();

        order.setId(10L);
        order.setTotal(150.75);
        order.setStatus("PAID");
        order.setCouponCode("DESCONTO10");

        assertEquals(10L, order.getId());
        assertEquals(150.75, order.getTotal());
        assertEquals("PAID", order.getStatus());
        assertEquals("DESCONTO10", order.getCouponCode());
    }

    @Test
    void testUserAssociation() {
        Order order = new Order();
        User user = new User();
        user.setId(3L);
        user.setUsername("gustavo");

        order.setUser(user);

        assertNotNull(order.getUser());
        assertEquals(3L, order.getUser().getId());
        assertEquals("gustavo", order.getUser().getUsername());
    }

    @Test
    void testPaymentAssociation() {
        Order order = new Order();
        Payment payment = new Payment();
        payment.setMethod("CREDIT_CARD");
        payment.setTransactionId("TX123");

        order.setPayment(payment);

        assertNotNull(order.getPayment());
        assertEquals("CREDIT_CARD", order.getPayment().getMethod());
        assertEquals("TX123", order.getPayment().getTransactionId());
    }

    @Test
    void testOrderItemsAssociation() {
        Order order = new Order();

        Book book = new Book();
        book.setId(1L);
        book.setTitle("Harry Potter");

        OrderItem item = new OrderItem();
        item.setBook(book);
        item.setQuantity(2);
        item.setPrice(29.90);
        item.setOrder(order);

        Set<OrderItem> items = new HashSet<>();
        items.add(item);

        order.setItems(items);

        assertNotNull(order.getItems());
        assertEquals(1, order.getItems().size());

        OrderItem retrieved = order.getItems().iterator().next();

        assertEquals(book, retrieved.getBook());
        assertEquals(2, retrieved.getQuantity());
        assertEquals(29.90, retrieved.getPrice());
        assertEquals(order, retrieved.getOrder());
    }

    // opcional, útil para relatório
    @Test
    void testEmptyOrderDefaults() {
        Order order = new Order();

        assertNull(order.getId());
        assertNull(order.getUser());
        assertNull(order.getItems());
        assertNull(order.getPayment());
        assertEquals(0.0, order.getTotal());
        assertNull(order.getStatus());
        assertNull(order.getCouponCode());
    }
}