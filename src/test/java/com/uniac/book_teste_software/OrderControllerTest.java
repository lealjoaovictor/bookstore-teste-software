package com.uniac.book_teste_software;

import com.uniac.book_teste_software.controller.OrderController;
import com.uniac.book_teste_software.model.Book;
import com.uniac.book_teste_software.model.Order;
import com.uniac.book_teste_software.model.OrderItem;
import com.uniac.book_teste_software.model.User;
import com.uniac.book_teste_software.repository.BookRepository;
import com.uniac.book_teste_software.repository.OrderRepository;
import com.uniac.book_teste_software.repository.UserRepository;
import com.uniac.book_teste_software.service.CouponService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OrderControllerTest {

    private OrderController controller;
    private OrderRepository orders;
    private UserRepository users;
    private BookRepository books;
    private CouponService couponService;

    @BeforeEach
    void setup() {
        orders = Mockito.mock(OrderRepository.class);
        users = Mockito.mock(UserRepository.class);
        books = Mockito.mock(BookRepository.class);
        couponService = Mockito.mock(CouponService.class);

        controller = new OrderController(orders, users, books, couponService);
    }

    @Test
    void testItemsNull() {
        Order o = new Order();
        o.setUser(new User());
        o.getUser().setId(1L);

        Mockito.when(users.findById(1L)).thenReturn(Optional.of(new User()));

        o.setItems(null);
        Mockito.when(orders.save(Mockito.any())).thenReturn(o);

        Order saved = controller.create(o);
        assertNotNull(saved.getItems());
        assertTrue(saved.getItems().isEmpty());
    }

    @Test
    void testUserNotFound() {
        Order o = new Order();
        o.setUser(new User());
        o.getUser().setId(99L);

        Mockito.when(users.findById(99L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(IllegalArgumentException.class, () -> controller.create(o));
        assertEquals("Usuário não encontrado", ex.getMessage());
    }

    @Test
    void testOrderItemWithoutBook() {
        Order o = new Order();
        o.setUser(new User());
        o.getUser().setId(1L);
        o.setItems(Set.of(new OrderItem()));

        Mockito.when(users.findById(1L)).thenReturn(Optional.of(new User()));

        Exception ex = assertThrows(IllegalArgumentException.class, () -> controller.create(o));
        assertEquals("OrderItem precisa ter book.id", ex.getMessage());
    }

    @Test
    void testBookNotFound() {
        Book b = new Book();
        b.setId(10L);

        OrderItem item = new OrderItem();
        item.setBook(b);
        item.setQuantity(1);

        Order o = new Order();
        o.setUser(new User());
        o.getUser().setId(1L);
        o.setItems(Set.of(item));

        Mockito.when(users.findById(1L)).thenReturn(Optional.of(new User()));
        Mockito.when(books.findById(10L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(IllegalArgumentException.class, () -> controller.create(o));
        assertEquals("Livro não encontrado: 10", ex.getMessage());
    }

    @Test
    void testValidOrderWithoutCoupon() {
        Book b = new Book();
        b.setId(1L);
        b.setPrice(50);

        OrderItem item = new OrderItem();
        item.setBook(b);
        item.setQuantity(2);

        Order o = new Order();
        o.setUser(new User());
        o.getUser().setId(1L);
        o.setItems(Set.of(item));

        Mockito.when(users.findById(1L)).thenReturn(Optional.of(new User()));
        Mockito.when(books.findById(1L)).thenReturn(Optional.of(b));
        Mockito.when(couponService.applyCoupon(o, null)).thenReturn(0.0);
        Mockito.when(orders.save(Mockito.any())).thenReturn(o);

        Order saved = controller.create(o);
        assertEquals(100, saved.getTotal());
    }

    @Test
    void testValidOrderWithCoupon() {
        Book b = new Book();
        b.setId(1L);
        b.setPrice(50);

        OrderItem item = new OrderItem();
        item.setBook(b);
        item.setQuantity(2);

        Order o = new Order();
        o.setUser(new User());
        o.getUser().setId(1L);
        o.setItems(Set.of(item));
        o.setCouponCode("X2");

        Mockito.when(users.findById(1L)).thenReturn(Optional.of(new User()));
        Mockito.when(books.findById(1L)).thenReturn(Optional.of(b));
        Mockito.when(couponService.applyCoupon(o, "X2")).thenReturn(10.0);
        Mockito.when(orders.save(Mockito.any())).thenReturn(o);

        Order saved = controller.create(o);
        assertEquals(90, saved.getTotal());
    }
}
