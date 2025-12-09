package com.uniac.book_teste_software;

import com.uniac.book_teste_software.model.*;
import com.uniac.book_teste_software.repository.CouponRepository;
import com.uniac.book_teste_software.service.CouponService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.transaction.Transactional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class CouponServiceFullTest {

    @Autowired
    private CouponService couponService;

    @Autowired
    private CouponRepository couponRepository;

    private User testUser;

    @BeforeEach
    void setup() {
        couponRepository.deleteAll(); // limpa todos os cupons
        testUser = new User();
        testUser.setId(99L);
        testUser.setEmail("normal@example.com");
        testUser.setOrders(Set.of()); // primeira compra
    }

    private Order createOrder(double[] prices, int[] quantities, String[] titles, Category category) {
        Order order = new Order();
        order.setUser(testUser);

        Set<OrderItem> items = new java.util.HashSet<>();
        for (int i = 0; i < prices.length; i++) {
            Book book = new Book();
            book.setId((long) i + 1);
            book.setTitle(titles[i]);
            book.setPrice(prices[i]);
            book.setCategory(category);

            OrderItem item = new OrderItem();
            item.setBook(book);
            item.setQuantity(quantities[i]);
            item.setOrder(order);

            items.add(item);
        }
        order.setItems(items);
        return order;
    }

    private Coupon saveCoupon(String code, double discountPercent, double discountValue,
                              boolean onlyFirstPurchase, boolean requiresMinValue,
                              boolean categorySpecific, String categoryName,
                              Double minValue) {  // <- novo parâmetro
        Coupon c = new Coupon();
        c.setCode(code);
        c.setDiscountPercent(discountPercent);
        c.setDiscountValue(discountValue);
        c.setOnlyFirstPurchase(onlyFirstPurchase);
        c.setRequiresMinValue(requiresMinValue);
        c.setCategorySpecific(categorySpecific);
        c.setCategoryName(categoryName);
        c.setMinValue(minValue);  // <- seta o valor mínimo
        return couponRepository.save(c);
    }


    @Test
    void testPercentualSimpleCoupon() {
        Order order = createOrder(new double[]{100}, new int[]{1}, new String[]{"Book1"}, null);
        saveCoupon("PERCENT10", 10.0, 0.0, false, false, false, null, 0.0);
        double discount = couponService.applyCoupon(order, "PERCENT10");
        System.out.println("Percentual simples: " + discount);
        assertTrue(discount > 0);
    }

    @Test
    void testFixedValueCoupon() {
        Order order = createOrder(new double[]{50}, new int[]{1}, new String[]{"Book2"}, null);
        saveCoupon(
                "FIXO20_TEST", // code
                0.0,           // discountPercent
                20.0,          // discountValue
                false,         // onlyFirstPurchase
                false,         // requiresMinValue
                false,         // categorySpecific
                null,          // categoryName
                0.0            // minValue, obrigatoriamente 0.0
        );
        double discount = couponService.applyCoupon(order, "FIXO20_TEST");
        System.out.println("Valor fixo: " + discount);
        assertTrue(discount > 0);
    }

    @Test
    void testFirstPurchaseCoupon() {
        Order order = createOrder(new double[]{50}, new int[]{1}, new String[]{"Book3"}, null);
        saveCoupon("FIRST20", 0.0, 0.0, true, false, false, null, 0.0);
        double discount = couponService.applyCoupon(order, "FIRST20");
        System.out.println("Primeira compra: " + discount);
        assertTrue(discount > 0);
    }

    @Test
    void testMinValueCoupon() {
        Order order = createOrder(new double[]{100}, new int[]{1}, new String[]{"Book4"}, null);
        saveCoupon(
                "MIN50_TEST", // código do cupom
                10.0,         // discountPercent
                0.0,          // discountValue
                false,        // onlyFirstPurchase
                true,         // requiresMinValue
                false,        // categorySpecific
                null,         // categoryName
                50.0          // minValue
        );
        double discount = couponService.applyCoupon(order, "MIN50_TEST");
        System.out.println("Valor mínimo: " + discount);
        assertTrue(discount > 0);
    }

    @Test
    void testCategorySpecificCoupon() {
        Category cat = new Category();
        cat.setName("Programming");
        Order order = createOrder(new double[]{100}, new int[]{1}, new String[]{"Book5"}, cat);
        saveCoupon("PROG10_TEST", 10.0, 0.0, false, false, true, "Programming", 0.0);
        double discount = couponService.applyCoupon(order, "PROG10_TEST");
        System.out.println("Categoria específica: " + discount);
        assertTrue(discount > 0);
    }

    @Test
    void testDoido50Coupon() {
        // criar order com quantidade total > 5
        Order order = createOrder(new double[]{50, 30}, new int[]{3,3}, new String[]{"Book6","Book7"}, null);
        saveCoupon("DOIDO50_TEST", 0.0, 0.0, false, false, false, null, 0.0);
        double discount = couponService.applyCoupon(order, "DOIDO50_TEST");
        System.out.println("DOIDO50_TEST: " + discount);
        assertTrue(discount > 0);

    }


    @Test
    void testVip30Coupon() {
        testUser.setEmail("premium@example.com"); // ativa o desconto VIP
        Order order = createOrder(
                new double[]{100, 50},           // preços
                new int[]{1, 2},                 // quantidades
                new String[]{"BookVIP1", "BookVIP2"}, // títulos
                null                             // categoria
        );
        saveCoupon("VIP30_TEST", 30.0, 0.0, false, false, false, null, 0.0); // cupom
        double discount = couponService.applyCoupon(order, "VIP30_TEST");
        System.out.println("VIP30_TEST: " + discount);
        assertTrue(discount > 0); // agora deve passar
    }


    @Test
    void testFreteGratisCoupon() {
        Order order = createOrder(new double[]{50}, new int[]{1}, new String[]{"Book9"}, null);
        saveCoupon(
                "FRETEGRATIS_TEST", // code
                0.0,                // discountPercent
                0.0,                // discountValue
                false,              // onlyFirstPurchase
                false,              // requiresMinValue
                false,              // categorySpecific
                null,               // categoryName
                0.0                 // minValue
        );
        double discount = couponService.applyCoupon(order, "FRETEGRATIS_TEST");
        System.out.println("FRETEGRATIS: " + discount);
        assertTrue(discount > 0);
    }

    @Test
    void testImport20Coupon() {
        Order order = createOrder(new double[]{50}, new int[]{1}, new String[]{"(Imported)Book10"}, null);
        saveCoupon(
                "IMPORT20_TEST", // code
                20.0,           // discountPercent
                0.0,            // discountValue
                false,          // onlyFirstPurchase
                false,          // requiresMinValue
                false,          // categorySpecific
                null,           // categoryName
                0.0             // minValue, obrigatório
        );
        double discount = couponService.applyCoupon(order, "IMPORT20_TEST");
        System.out.println("IMPORT20: " + discount);
        assertTrue(discount > 0);
    }

    @Test
    void testQuaselaCoupon() {
        Order order = createOrder(new double[]{300}, new int[]{1}, new String[]{"Book11"}, null);
        saveCoupon(
                "QUASELA_TEST", // pode ter _TEST
                0.0,
                0.0,
                false,
                false,
                false,
                null,
                0.0
        );

        double discount = couponService.applyCoupon(order, "QUASELA_TEST");
        System.out.println("QUASELA: " + discount);
        assertTrue(discount > 0);
    }
}
