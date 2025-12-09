package com.uniac.book_teste_software;

import com.uniac.book_teste_software.model.*;
import com.uniac.book_teste_software.service.CouponService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class AdvancedCouponsTest {

    @Autowired
    private CouponService service;

    private Order createOrder(double total) {
        Order o = new Order();
        o.setTotal(total);
        return o;
    }

    @Test
    void testMega() {
        Order o = createOrder(100);
        double d = service.applyCoupon(o, "MEGA123");
        assertEquals(12.0, d, 0.0001);
    }

    @Test
    void testSuper() {
        Order o = createOrder(200);
        double d = service.applyCoupon(o, "SUPER90");
        assertEquals(30.0, d, 0.0001);
    }

    @Test
    void testBonus5() {
        Order o = createOrder(70);
        double d = service.applyCoupon(o, "BONUS5");
        assertEquals(5, d, 0.0001);
    }

    @Test
    void testBonus10() {
        Order o = createOrder(70);
        double d = service.applyCoupon(o, "BONUS10");
        assertEquals(10, d, 0.0001);
    }

    @Test
    void testBonus20() {
        Order o = createOrder(100);
        double d = service.applyCoupon(o, "BONUS20");
        assertEquals(20, d, 0.0001);
    }

    @Test
    void testPercent5() {
        Order o = createOrder(100);
        double d = service.applyCoupon(o, "PERCENT5");
        assertEquals(5, d, 0.0001);
    }

    @Test
    void testPercent7() {
        Order o = createOrder(100);
        double d = service.applyCoupon(o, "PERCENT7");
        assertEquals(7, d, 0.0001);
    }

    @Test
    void testPercent9() {
        Order o = createOrder(100);
        double d = service.applyCoupon(o, "PERCENT9");
        assertEquals(9, d, 0.0001);
    }

    @Test
    void testVip() {
        Order o = createOrder(200);
        double d = service.applyCoupon(o, "VIP");
        assertEquals(26.0, d, 0.0001);
    }

    @Test
    void testPlatinum() {
        Order o = createOrder(250);
        double d = service.applyCoupon(o, "PLATINUM");
        assertEquals(50.0, d, 0.0001);
    }
}
