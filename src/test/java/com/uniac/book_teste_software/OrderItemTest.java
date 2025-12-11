package com.uniac.book_teste_software;

import com.uniac.book_teste_software.model.Book;
import com.uniac.book_teste_software.model.Order;
import com.uniac.book_teste_software.model.OrderItem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OrderItemTest {

    @Test
    void testBasicFields() {
        OrderItem item = new OrderItem();

        item.setId(5L);
        item.setQuantity(3);
        item.setPrice(49.90);

        assertEquals(5L, item.getId());
        assertEquals(3, item.getQuantity());
        assertEquals(49.90, item.getPrice());
    }

    @Test
    void testBookAssociation() {
        OrderItem item = new OrderItem();

        Book book = new Book();
        book.setId(10L);
        book.setTitle("Clean Code");
        book.setPrice(80.00);

        item.setBook(book);

        assertNotNull(item.getBook());
        assertEquals(10L, item.getBook().getId());
        assertEquals("Clean Code", item.getBook().getTitle());
        assertEquals(80.00, item.getBook().getPrice());
    }

    @Test
    void testOrderAssociation() {
        OrderItem item = new OrderItem();

        Order order = new Order();
        order.setId(100L);
        order.setStatus("PAID");

        item.setOrder(order);

        assertNotNull(item.getOrder());
        assertEquals(100L, item.getOrder().getId());
        assertEquals("PAID", item.getOrder().getStatus());
    }

    @Test
    void testItemBelongsToOrderAndReferencesBook() {
        // Book
        Book book = new Book();
        book.setId(7L);
        book.setTitle("Harry Potter");
        book.setPrice(30.00);

        // Order
        Order order = new Order();
        order.setId(2L);

        // OrderItem
        OrderItem item = new OrderItem();
        item.setBook(book);
        item.setOrder(order);
        item.setQuantity(2);
        item.setPrice(book.getPrice());

        assertEquals(7L, item.getBook().getId());
        assertEquals("Harry Potter", item.getBook().getTitle());
        assertEquals(order, item.getOrder());
        assertEquals(2, item.getQuantity());
        assertEquals(30.00, item.getPrice());
    }

    @Test
    void testDefaultValues() {
        OrderItem item = new OrderItem();

        assertNull(item.getId());
        assertNull(item.getBook());
        assertNull(item.getOrder());
        assertEquals(0, item.getQuantity());
        assertEquals(0.0, item.getPrice());
    }
}