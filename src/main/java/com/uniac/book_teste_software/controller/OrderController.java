package com.uniac.book_teste_software.controller;

import com.uniac.book_teste_software.model.Order;
import com.uniac.book_teste_software.model.OrderItem;
import com.uniac.book_teste_software.repository.OrderRepository;
import com.uniac.book_teste_software.repository.UserRepository;
import com.uniac.book_teste_software.repository.BookRepository;

import com.uniac.book_teste_software.service.CouponService;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderRepository orders;
    private final UserRepository users;
    private final BookRepository books;
    private final CouponService couponService;

    public OrderController(OrderRepository orders, UserRepository users, BookRepository books, CouponService couponService) {
        this.orders = orders;
        this.users = users;
        this.books = books;
        this.couponService = couponService;
    }

    @GetMapping
    @Transactional
    public List<Order> list() {
        return orders.findAll();
    }


    @PostMapping
    public Order create(@RequestBody Order order) {

        if (order.getItems() == null) { // 1
            order.setItems(new HashSet<>()); // 2
        }

        var user = users.findById(order.getUser().getId()) // 3
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado")); // 4
        order.setUser(user);

        for (OrderItem item : order.getItems()) { // 5

            if (item.getBook() == null || item.getBook().getId() == null) { // 6 // 7
                throw new IllegalArgumentException("OrderItem precisa ter book.id"); // 8
            }

            var book = books.findById(item.getBook().getId()) // 9
                    .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado: " + item.getBook().getId())); // 10
            item.setBook(book);
            item.setOrder(order);
        } // 11

        double subtotal = order.getItems().stream() // 12
                .mapToDouble(i -> i.getBook().getPrice() * i.getQuantity())
                .sum();

        double discount = couponService.applyCoupon(order, order.getCouponCode());

        double finalValue = Math.max(0, subtotal - discount);

        order.setTotal(finalValue);

        return orders.save(order); // 13
    } // 14

    @GetMapping("/{id}")
    public Order getById(@PathVariable Long id) {
        return orders.findById(id).orElseThrow();
    }

}
