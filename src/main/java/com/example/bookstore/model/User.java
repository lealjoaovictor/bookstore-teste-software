package com.example.bookstore.model;


import jakarta.persistence.*;
import java.util.Set;


@Entity
public class User {
    @Id @GeneratedValue
    private Long id;
    private String username;
    private String email;

    @OneToMany(mappedBy = "user")
    private Set<Order> orders;

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}