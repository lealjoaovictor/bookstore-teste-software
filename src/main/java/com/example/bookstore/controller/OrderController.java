package com.example.bookstore.controller;

import com.example.bookstore.dto.OrderRequest;
import com.example.bookstore.model.Order;
import com.example.bookstore.service.OrderService;
import org.springframework.web.bind.annotation.*;

/**
 * Endpoints para criar/processar pedidos.
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Recebe um OrderRequest (DTO) em JSON, converte para OrderService.OrderRequestData e processa.
     * Exemplo de JSON:
     * {
     *   "items": {"1":2, "3":1},
     *   "paymentMethod": "CREDIT_CARD",
     *   "cardVerification": true
     * }
     */
    @PostMapping("/process")
    public Order process(@RequestBody OrderRequest req) {
        return orderService.processOrder(req.toOrderRequestData());
    }
}
