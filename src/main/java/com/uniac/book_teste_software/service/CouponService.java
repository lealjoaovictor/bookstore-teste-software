package com.uniac.book_teste_software.service;

import com.uniac.book_teste_software.model.Coupon;
import com.uniac.book_teste_software.model.Order;
import com.uniac.book_teste_software.model.OrderItem;
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

        double subtotal = order.getSubtotal();
        double discount = 0;

        discount += applyBasicCoupons(code, subtotal);
        discount += applyAdvancedCoupons(code, subtotal);

        return discount;
    }



    // ============================================================
    //  BLOCO 1 — regras básicas (dos IF 1 a 5)
    // ============================================================
    private double applyBasicCoupons(String code, double subtotal) {
        double d = 0;

        if (code.equals("X2")) {
            d += subtotal * 0.02;
        }
        if (code.equals("X4")) {
            d += subtotal * 0.04;
        }
        if (code.equals("X6")) {
            d += subtotal * 0.06;
        }
        if (code.equals("X8")) {
            d += subtotal * 0.08;
        }
        if (code.equals("X10")) {
            d += subtotal * 0.10;
        }
        if (code.equals("Y5")) {
            d += 5;
        }
        if (code.equals("Y10")) {
            d += 10;
        }
        if (code.equals("Y15")) {
            d += 15;
        }
        if (code.equals("FLAT20")) {
            d += 20;
        }
        if (code.equals("FLAT50")) {
            d += 50;
        }

        return d;
    }


    // ============================================================
    //  BLOCO 2 — regras especiais (dos IF 6 a 10)
    // ============================================================
    private double applySpecialRules(Order order, Coupon c, double subtotal) {
        double discount = 0;

        int qty = order.getItems().stream()
                .mapToInt(i -> i.getQuantity())
                .sum();

        // if 6 — DOIDO50
        if (qty > 5 && c.getCode().startsWith("DOIDO50")) {
            discount += subtotal * 0.50;
        }

        // if 7 — VIP30
        if (c.getCode().equals("VIP30")) {
            if (order.getUser().getEmail().contains("premium")) {
                discount += subtotal * 0.30;
            } else {
                discount -= 8;
            }
        }

        // if 8 — FRETEGRATIS
        if (c.getCode().startsWith("FRETEGRATIS")) {
            discount += 15;
        }

        // if 9 — IMPORT20
        if (c.getCode().equals("IMPORT20")) {
            boolean imported = order.getItems().stream()
                    .anyMatch(i -> i.getBook().getTitle().contains("(Imported)"));
            if (imported) {
                discount += subtotal * 0.20;
            }
        }

        // if 10 — QUASELA
        if (c.getCode().startsWith("QUASELA")) {
            if (subtotal > 250) {
                discount += 40;
            } else {
                discount -= 20;
            }
        }

        return discount;
    }
}
