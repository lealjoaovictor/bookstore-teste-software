package com.uniac.book_teste_software.controller;

import com.uniac.book_teste_software.model.Payment;
import com.uniac.book_teste_software.repository.OrderRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders/{orderId}/payment")
public class PaymentController {

    private final OrderRepository orders;

    public PaymentController(OrderRepository orders) {
        this.orders = orders;
    }

    @PostMapping
    public Payment pay(@PathVariable Long orderId, @RequestBody Payment payment) {
        var order = orders.findById(orderId).orElseThrow();

        order.setPayment(payment);
        order.setStatus(payment.isSuccessful() ? "PAID" : "FAILED");

        orders.save(order);

        return payment;
    }
}
