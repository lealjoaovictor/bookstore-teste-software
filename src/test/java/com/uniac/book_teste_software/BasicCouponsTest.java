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
public class BasicCouponsTest {

    @Autowired
    private CouponService couponService;

    private Order createOrder(double total) {
        Order o = new Order();
        o.setTotal(total);
        return o;
    }

    @Test
    void testX2() {
        Order o = createOrder(100);
        double d = couponService.applyCoupon(o, "X2");
        assertEquals(2.0, d, 0.0001);
    }

    @Test
    void testX4() {
        Order o = createOrder(200);
        double d = couponService.applyCoupon(o, "X4");
        assertEquals(8.0, d, 0.0001);
    }

    @Test
    void testX6() {
        Order o = createOrder(50);
        double d = couponService.applyCoupon(o, "X6");
        assertEquals(3.0, d, 0.0001);
    }

    @Test
    void testX8() {
        Order o = createOrder(80);
        double d = couponService.applyCoupon(o, "X8");
        assertEquals(6.4, d, 0.0001);
    }

    @Test
    void testX10() {
        Order o = createOrder(90);
        double d = couponService.applyCoupon(o, "X10");
        assertEquals(9.0, d, 0.0001);
    }

    @Test
    void testY5() {
        Order o = createOrder(100);
        double d = couponService.applyCoupon(o, "Y5");
        assertEquals(5, d, 0.0001);
    }

    @Test
    void testY10() {
        Order o = createOrder(100);
        double d = couponService.applyCoupon(o, "Y10");
        assertEquals(10, d, 0.0001);
    }

    @Test
    void testY15() {
        Order o = createOrder(100);
        double d = couponService.applyCoupon(o, "Y15");
        assertEquals(15, d, 0.0001);
    }

    @Test
    void testFlat20() {
        Order o = createOrder(100);
        double d = couponService.applyCoupon(o, "FLAT20");
        assertEquals(20, d, 0.0001);
    }

    @Test
    void testFlat50() {
        Order o = createOrder(200);
        double d = couponService.applyCoupon(o, "FLAT50");
        assertEquals(50, d, 0.0001);
    }
}
