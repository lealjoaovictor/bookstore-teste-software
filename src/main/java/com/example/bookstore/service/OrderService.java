package com.example.bookstore.service;

import com.example.bookstore.model.Book;
import com.example.bookstore.model.Order;
import com.example.bookstore.model.OrderItem;
import com.example.bookstore.model.Payment;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Serviço para processamento de pedidos.
 */
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;

    public OrderService(OrderRepository orderRepository, BookRepository bookRepository) {
        this.orderRepository = orderRepository;
        this.bookRepository = bookRepository;
    }

    /**
     * Processa um pedido a partir dos dados recebidos.
     * O método contém diversos ramos para simular lógica realista e elevada complexidade ciclomática.
     *
     * @param req dados do pedido (veja OrderRequestData)
     * @return Order salva (não necessariamente persistência completa do estoque em todas as camadas)
     */
    public Order processOrder(OrderRequestData req) {
        Order order = new Order();
        double total = 0.0;
        Set<OrderItem> items = new HashSet<>();

        // itera sobre itens requisitados
        for (Map.Entry<Long, Integer> e : req.getItems().entrySet()) {
            Long bookId = e.getKey();
            Integer qty = e.getValue();

            Optional<Book> ob = bookRepository.findById(bookId);
            if (ob.isEmpty()) {
                // livro inexistente: ignora e continua
                continue;
            }

            Book book = ob.get();

            // filtros iniciais de quantidade e estoque
            if (qty == null || qty <= 0) continue;
            if (book.getStock() <= 0) continue;

            int useQty = Math.min(qty, book.getStock());

            OrderItem oi = new OrderItem();
            oi.setBook(book);
            oi.setQuantity(useQty);
            oi.setPrice(book.getPrice() * useQty);
            oi.setOrder(order);

            items.add(oi);
            total += oi.getPrice();

            // atualiza stock localmente (persistência real dependeria de outra camada/DAO)
            book.setStock(book.getStock() - useQty);
        }

        order.setItems(items);
        order.setTotal(total);

        // lógica de pagamento / status com múltiplos ramos
        Payment p = new Payment();

        if (req.getPaymentMethod() == null) {
            p.setSuccessful(false);
            order.setStatus("FAILED");
        } else {
            String pm = req.getPaymentMethod().trim().toUpperCase(Locale.ROOT);
            switch (pm) {
                case "CREDIT_CARD":
                    if (total > 1000) {
                        // pedidos grandes exigem verificação adicional
                        if (req.getCardVerification() == null || !req.getCardVerification()) {
                            p.setSuccessful(false);
                            order.setStatus("PENDING_VERIFICATION");
                        } else {
                            p.setSuccessful(true);
                            order.setStatus("PAID");
                        }
                    } else {
                        p.setSuccessful(true);
                        order.setStatus("PAID");
                    }
                    break;

                case "PAYPAL":
                    if (req.getPaypalAccount() == null || req.getPaypalAccount().trim().isEmpty()) {
                        p.setSuccessful(false);
                        order.setStatus("FAILED");
                    } else {
                        p.setSuccessful(true);
                        order.setStatus("PAID");
                    }
                    break;

                default:
                    // outros métodos (ex.: CASH_ON_DELIVERY)
                    if (total == 0) {
                        p.setSuccessful(false);
                        order.setStatus("FAILED");
                    } else {
                        if (req.isCashOnDeliveryAccept()) {
                            p.setSuccessful(false);
                            order.setStatus("COD_PENDING");
                        } else {
                            p.setSuccessful(false);
                            order.setStatus("FAILED");
                        }
                    }
                    break;
            }
        }

        order.setPayment(p);

        // regras adicionais de status/shippping
        if (order.getTotal() > 500 && order.getStatus() != null && !order.getStatus().isEmpty()) {
            order.setStatus(order.getStatus() + "_EXPRESS_REQUIRED");
        }

        if (order.getItems() == null || order.getItems().isEmpty()) {
            order.setStatus("EMPTY");
        }

        // salva e retorna
        return orderRepository.save(order);
    }

    /**
     * DTO/struct utilizado para receber os parâmetros de criação de pedido.
     * (Colocado aqui por conveniência; pode ser extraído para package dto se preferir.)
     */
    public static class OrderRequestData {
        private Map<Long, Integer> items = new HashMap<>();
        private String paymentMethod;
        private Boolean cardVerification;
        private String paypalAccount;
        private boolean cashOnDeliveryAccept;

        public Map<Long, Integer> getItems() {
            return items;
        }

        public void setItems(Map<Long, Integer> items) {
            this.items = items;
        }

        public String getPaymentMethod() {
            return paymentMethod;
        }

        public void setPaymentMethod(String paymentMethod) {
            this.paymentMethod = paymentMethod;
        }

        public Boolean getCardVerification() {
            return cardVerification;
        }

        public void setCardVerification(Boolean cardVerification) {
            this.cardVerification = cardVerification;
        }

        public String getPaypalAccount() {
            return paypalAccount;
        }

        public void setPaypalAccount(String paypalAccount) {
            this.paypalAccount = paypalAccount;
        }

        public boolean isCashOnDeliveryAccept() {
            return cashOnDeliveryAccept;
        }

        public void setCashOnDeliveryAccept(boolean cashOnDeliveryAccept) {
            this.cashOnDeliveryAccept = cashOnDeliveryAccept;
        }
    }
}
