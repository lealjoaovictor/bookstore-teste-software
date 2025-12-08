package com.uniac.book_teste_software.controller;

import com.uniac.book_teste_software.model.Order;
import com.uniac.book_teste_software.model.OrderItem;
import com.uniac.book_teste_software.repository.OrderRepository;
import com.uniac.book_teste_software.repository.UserRepository;
import com.uniac.book_teste_software.repository.BookRepository;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderRepository orders;
    private final UserRepository users;
    private final BookRepository books;

    public OrderController(OrderRepository orders, UserRepository users, BookRepository books) {
        this.orders = orders;
        this.users = users;
        this.books = books;
    }

    @GetMapping
    public List<Order> list() {
        return orders.findAll();
    }

    @PostMapping
    public Order create(@RequestBody Order order) {

        // garantir que o usuário está anexado ao contexto JPA
        var user = users.findById(order.getUser().getId()).orElseThrow();
        order.setUser(user);

        // anexar itens
        if (order.getItems() != null) {
            for (OrderItem item : order.getItems()) {
                var book = books.findById(item.getBook().getId()).orElseThrow();
                item.setBook(book);
                item.setOrder(order);
            }
        }

        return orders.save(order);
    }
}
