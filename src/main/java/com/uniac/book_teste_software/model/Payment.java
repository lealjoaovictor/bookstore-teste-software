package com.uniac.book_teste_software.model;


import jakarta.persistence.*;


@Entity
public class Payment {
    @Id @GeneratedValue
    private Long id;
    private String method; // CREDIT_CARD, PAYPAL, DPS CRIAR UM ENUM
    private String transactionId;
    private boolean successful;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }
}