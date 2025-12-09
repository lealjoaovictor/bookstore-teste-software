package com.uniac.book_teste_software.model;

import jakarta.persistence.*;

@Entity
public class Coupon {

    @Id
    @GeneratedValue
    private Long id;

    private String code;
    private Double discountValue; // valor fixo
    private Double discountPercent; // desconto percentual
    private boolean onlyFirstPurchase;
    private boolean requiresMinValue;
    private Double minValue;
    private boolean categorySpecific;
    private String categoryName;

    public Coupon(String code, Double discountPercent, Double discountValue) {
        this.code = code;
        this.discountPercent = discountPercent;
        this.discountValue = discountValue;
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

    public double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(double discountValue) {
        this.discountValue = discountValue;
    }

    public double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(double discountPercent) {
        this.discountPercent = discountPercent;
    }

    public boolean isOnlyFirstPurchase() {
        return onlyFirstPurchase;
    }

    public void setOnlyFirstPurchase(boolean onlyFirstPurchase) {
        this.onlyFirstPurchase = onlyFirstPurchase;
    }

    public boolean isRequiresMinValue() {
        return requiresMinValue;
    }

    public void setRequiresMinValue(boolean requiresMinValue) {
        this.requiresMinValue = requiresMinValue;
    }

    public double getMinValue() {
        return minValue;
    }

    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }

    public boolean isCategorySpecific() {
        return categorySpecific;
    }

    public void setCategorySpecific(boolean categorySpecific) {
        this.categorySpecific = categorySpecific;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
