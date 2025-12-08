package com.uniac.book_teste_software.controller;

import com.uniac.book_teste_software.model.Order;
import com.uniac.book_teste_software.model.OrderItem;
import com.uniac.book_teste_software.repository.OrderRepository;
import com.uniac.book_teste_software.repository.UserRepository;
import com.uniac.book_teste_software.repository.BookRepository;

import jakarta.transaction.Transactional;
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
    @Transactional
    public List<Order> list() {
        return orders.findAll().stream()
                .map(o -> {
                    Order dto = new Order();
                    dto.setId(o.getId());
                    dto.setStatus(o.getStatus());
                    dto.setTotal(o.getTotal());
                    dto.setItems(o.getItems());
                    // NÃO incluí user
                    return dto;
                })
                .toList();

    }

    @PostMapping
    public Order create(@RequestBody Order order) {

        var user = users.findById(order.getUser().getId()).orElseThrow();
        order.setUser(user);

        if (order.getItems() != null) {
            for (OrderItem item : order.getItems()) {

                if (item.getBook() == null || item.getBook().getId() == null) {
                    throw new IllegalArgumentException("OrderItem precisa ter book.id");
                }

                var book = books.findById(item.getBook().getId()).orElseThrow();
                item.setBook(book);
                item.setOrder(order);
            }
        }

        return orders.save(order);
    }

}
