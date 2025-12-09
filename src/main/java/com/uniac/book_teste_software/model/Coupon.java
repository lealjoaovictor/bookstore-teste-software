package com.uniac.book_teste_software.model;

import jakarta.persistence.*;

@Entity
public class Coupon {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String code;

    public Coupon(String code) {
        this.code = code;

    }

    public Coupon() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
