package com.uniac.book_teste_software.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;


@Entity
public class Book {
    @Id @GeneratedValue
    private Long id;
    private String title;
    private String isbn;
    private double price;
    private int stock;


    @ManyToOne(fetch = FetchType.EAGER)
    private Author author;


    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"books"})
    private Category category;

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}