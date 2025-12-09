package com.uniac.book_teste_software;

import com.uniac.book_teste_software.model.*;
import com.uniac.book_teste_software.repository.CouponRepository;
import com.uniac.book_teste_software.service.CouponService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Autowired
    private CouponRepository couponRepository;

    @Test
    void testDoido50Coupon() {
        Order order = new Order();

        // setup usuário com orders vazias para primeira compra
        User user = new User();
        user.setId(2L);
        user.setOrders(Set.of()); // garante ordersCount = 0
        order.setUser(user);

        // setup itens
        OrderItem item1 = new OrderItem();
        Book book1 = new Book();
        book1.setId(1L);
        book1.setPrice(50.0);
        item1.setBook(book1);
        item1.setQuantity(3);
        item1.setOrder(order);

        OrderItem item2 = new OrderItem();
        Book book2 = new Book();
        book2.setId(2L);
        book2.setPrice(30.0);
        item2.setBook(book2);
        item2.setQuantity(3);
        item2.setOrder(order);

        order.setItems(Set.of(item1, item2));

        // criar cupom
        Coupon c = new Coupon();
        c.setCode("DOIDO50_TESTE");
        c.setDiscountPercent(50);
        c.setDiscountValue(0.0); // evita NullPointerException
        couponRepository.save(c);

        double discount = couponService.applyCoupon(order, "DOIDO50_TESTE");
        System.out.println("Discount applied: " + discount);

        assertTrue(discount > 0, "O desconto deve ser aplicado corretamente");
    }
}
