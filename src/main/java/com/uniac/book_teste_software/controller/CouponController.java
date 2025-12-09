package com.uniac.book_teste_software.controller;

import com.uniac.book_teste_software.model.Coupon;
import com.uniac.book_teste_software.repository.CouponRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coupons")
public class CouponController {

    private final CouponRepository coupons;

    public CouponController(CouponRepository coupons) {
        this.coupons = coupons;
    }

    @GetMapping
    public List<Coupon> getAllCoupons() {
        return coupons.findAll();
    }
}
