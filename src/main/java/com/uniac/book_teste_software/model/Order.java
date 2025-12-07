package com.uniac.book_teste_software.model;


import jakarta.persistence.*;
import java.util.Set;


@Entity
@Table(name = "orders")
public class Order {
    @Id @GeneratedValue
    private Long id;
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<OrderItem> items;
    private double total;
    private String status; // NEW, PAID, SHIPPED, CANCELLED, DPS CRIAR UM ENUM
    @OneToOne(cascade = CascadeType.ALL)
    private Payment payment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<OrderItem> getItems() {
        return items;
    }

    public void setItems(Set<OrderItem> items) {
        this.items = items;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}