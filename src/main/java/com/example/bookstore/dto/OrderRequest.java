package com.example.bookstore.dto;

import com.example.bookstore.service.OrderService;

import java.util.HashMap;
import java.util.Map;

/**
 * DTO utilizado para receber os dados do pedido via JSON no controller.
 * Exemplo de JSON esperado:
 * {
 *   "items": {"1": 2, "3": 1},
 *   "paymentMethod": "CREDIT_CARD",
 *   "cardVerification": true,
 *   "paypalAccount": "user@paypal.com",
 *   "cashOnDeliveryAccept": false
 * }
 */
public class OrderRequest {

    private Map<Long, Integer> items = new HashMap<>();
    private String paymentMethod;
    private Boolean cardVerification;
    private String paypalAccount;
    private boolean cashOnDeliveryAccept;

    public OrderRequest() {}

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

    /**
     * Converte este DTO para a classe interna OrderRequestData do OrderService.
     * Isso facilita o uso no controller sem acoplar diretamente as estruturas do serviço.
     */
    public OrderService.OrderRequestData toOrderRequestData() {
        OrderService.OrderRequestData data = new OrderService.OrderRequestData();

        if (this.items != null) {
            data.setItems(new HashMap<>(this.items));
        }
        data.setPaymentMethod(this.paymentMethod);
        data.setCardVerification(this.cardVerification);
        data.setPaypalAccount(this.paypalAccount);
        data.setCashOnDeliveryAccept(this.cashOnDeliveryAccept);
        return data;
    }
}
