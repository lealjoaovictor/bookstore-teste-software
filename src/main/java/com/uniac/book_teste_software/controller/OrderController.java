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
        return orders.findAll(); // já inclui o user, sem precisar criar novo Order
    }


    @PostMapping
    public Order create(@RequestBody Order order) {

        // inicializa items se estiver null
        if (order.getItems() == null) {
            order.setItems(new HashSet<>());
        }

        // carregar usuário
        var user = users.findById(order.getUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        order.setUser(user);

        // carregar itens com seus livros reais
        for (OrderItem item : order.getItems()) {

            if (item.getBook() == null || item.getBook().getId() == null) {
                throw new IllegalArgumentException("OrderItem precisa ter book.id");
            }

            var book = books.findById(item.getBook().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado: " + item.getBook().getId()));
            item.setBook(book);
            item.setOrder(order);
        }

        // ================================
        // CALCULA SUBTOTAL
        // ================================
        double subtotal = order.getItems().stream()
                .mapToDouble(i -> i.getBook().getPrice() * i.getQuantity())
                .sum();

        // ================================
        // APLICA CUPOM
        // ================================
        double discount = couponService.applyCoupon(order, order.getCouponCode());

        // ================================
        // CALCULA TOTAL FINAL
        // ================================
        double finalValue = Math.max(0, subtotal - discount);

        order.setTotal(finalValue);

        // salva o pedido com o preço final
        return orders.save(order);
    }

    @GetMapping("/{id}")
    public Order getById(@PathVariable Long id) {
        return orders.findById(id).orElseThrow();
    }

}
