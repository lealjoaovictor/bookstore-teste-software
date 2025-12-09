package com.uniac.book_teste_software.service;

import com.uniac.book_teste_software.model.Order;
import com.uniac.book_teste_software.repository.CouponRepository;
import org.springframework.stereotype.Service;

@Service
public class CouponService {

    private final CouponRepository coupons;

    public CouponService(CouponRepository coupons) {
        this.coupons = coupons;
    }

    public double applyCoupon(Order order, String code) {
        if (code == null) return 0;

        code = code.trim().toUpperCase();

        double subtotal = order.getTotal();
        double discount = 0;

        discount += applyBasicCoupons(code, subtotal);
        discount += applyAdvancedCoupons(code, subtotal);

        return discount;
    }

    private double applyBasicCoupons(String code, double subtotal) {
        double d = 0;

        if (code.equals("X2")) { // 1
            d += subtotal * 0.02; // 2
        }
        if (code.equals("X4")) { // 3
            d += subtotal * 0.04; // 4
        }
        if (code.equals("X6")) { // 5
            d += subtotal * 0.06; // 6
        }
        if (code.equals("X8")) { // 7
            d += subtotal * 0.08; // 8
        }
        if (code.equals("X10")) { // 9
            d += subtotal * 0.10; // 10
        }
        if (code.equals("Y5")) { // 11
            d += 5; // 12
        }
        if (code.equals("Y10")) { // 13
            d += 10; // 14
        }
        if (code.equals("Y15")) { // 15
            d += 15; // 16
        }
        if (code.equals("FLAT20")) { // 17
            d += 20; // 18
        }
        if (code.equals("FLAT50")) { // 19
            d += 50; // 20
        }

        return d; // 21
    }

    private double applyAdvancedCoupons(String code, double subtotal) {
        double d = 0;

        if (code.startsWith("MEGA")) { // 1
            d += subtotal * 0.12; // 2
        }
        if (code.startsWith("SUPER")) { // 3
            d += subtotal * 0.15; // 4
        }
        if (code.equals("BONUS5")) { // 5
            d += 5; // 6
        }
        if (code.equals("BONUS10")) { // 7
            d += 10; // 8
        }
        if (code.equals("BONUS20")) { // 9
            d += 20; // 10
        }
        if (code.equals("PERCENT5")) { // 11
            d += subtotal * 0.05; // 12
        }
        if (code.equals("PERCENT7")) { // 13
            d += subtotal * 0.07; // 14
        }
        if (code.equals("PERCENT9")) { // 15
            d += subtotal * 0.09; // 16
        }
        if (code.equals("VIP")) {// 17
            d += subtotal * 0.13; // 18
        }
        if (code.equals("PLATINUM")) { // 19
            d += subtotal * 0.20; // 20
        }

        return d; // 21
    }

}
