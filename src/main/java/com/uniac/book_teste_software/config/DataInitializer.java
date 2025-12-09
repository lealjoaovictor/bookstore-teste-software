package com.uniac.book_teste_software.config;

import com.uniac.book_teste_software.model.*;
import com.uniac.book_teste_software.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Set;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(
            BookRepository books,
            AuthorRepository authors,
            CategoryRepository categories,
            UserRepository users,
            OrderRepository orders,
            OrderItemRepository orderItems,
            PaymentRepository payments,
            CouponRepository coupons
    ) {
        return args -> {

            System.out.println("🚀 Inicializando dados fixos no H2...");

            // ===========================
            // USERS
            // ===========================
            if (users.count() == 0) {
                User u1 = new User();
                u1.setUsername("admin");
                u1.setEmail("admin@example.com");
                users.save(u1);

                User u2 = new User();
                u2.setUsername("gustavo");
                u2.setEmail("gustavo@example.com");
                users.save(u2);

                User u3 = new User();
                u3.setUsername("joao");
                u3.setEmail("joao@example.com");
                users.save(u3);

                System.out.println("👤 Usuários criados: admin, john, mary");
            }

            // ===========================
            // BOOKS, AUTHORS, CATEGORIES
            // ===========================
            if (books.count() == 0) {

                // AUTHORS
                Author rowling = new Author();
                rowling.setName("J. K. Rowling");
                authors.save(rowling);

                Author martin = new Author();
                martin.setName("George R. R. Martin");
                authors.save(martin);

                // CATEGORIES
                Category fantasy = new Category();
                fantasy.setName("Fantasy");
                categories.save(fantasy);

                Category tech = new Category();
                tech.setName("Technology");
                categories.save(tech);

                // BOOK 1
                Book hp = new Book();
                hp.setTitle("Harry Potter and the Philosopher's Stone");
                hp.setIsbn("HP-001");
                hp.setPrice(29.90);
                hp.setStock(50);
                hp.setAuthor(rowling);
                hp.setCategory(fantasy);
                books.save(hp);

                // BOOK 2
                Book got = new Book();
                got.setTitle("A Game of Thrones");
                got.setIsbn("GOT-001");
                got.setPrice(49.90);
                got.setStock(30);
                got.setAuthor(martin);
                got.setCategory(fantasy);
                books.save(got);

                // BOOK 3
                Book clean = new Book();
                clean.setTitle("Clean Code");
                clean.setIsbn("CC-001");
                clean.setPrice(89.90);
                clean.setStock(20);
                clean.setCategory(tech);
                books.save(clean);

                System.out.println("📚 Livros criados: HP, GOT, Clean Code");
            }

            // =====================================
            // ORDERS DE TESTE
            // =====================================
            if (orders.count() == 0) {

                User admin = users.findById(1L).orElseThrow();
                User john  = users.findById(2L).orElseThrow();

                Book hp    = books.findById(1L).orElseThrow();
                Book got   = books.findById(2L).orElseThrow();
                Book clean = books.findById(3L).orElseThrow();

                // -------- ORDER 1: Admin compra 1 HP --------
                Order o1 = new Order();
                o1.setUser(admin);
                o1.setStatus("PAID");

                Payment p1 = new Payment();
                p1.setMethod("CREDIT_CARD");
                p1.setTransactionId("TX-ADMIN-001");
                p1.setSuccessful(true);
                o1.setPayment(p1);

                OrderItem i1 = new OrderItem();
                i1.setOrder(o1);
                i1.setBook(hp);
                i1.setQuantity(1);
                i1.setPrice(hp.getPrice());

                o1.setItems(Set.of(i1));
                o1.setTotal(i1.getPrice() * i1.getQuantity());

                orders.save(o1);

                // -------- ORDER 2: John compra 2 GOT + 1 Clean Code --------
                Order o2 = new Order();
                o2.setUser(john);
                o2.setStatus("SHIPPED");

                Payment p2 = new Payment();
                p2.setMethod("PAYPAL");
                p2.setTransactionId("TX-JOHN-002");
                p2.setSuccessful(true);
                o2.setPayment(p2);

                OrderItem i2a = new OrderItem();
                i2a.setOrder(o2);
                i2a.setBook(got);
                i2a.setQuantity(2);
                i2a.setPrice(got.getPrice());

                OrderItem i2b = new OrderItem();
                i2b.setOrder(o2);
                i2b.setBook(clean);
                i2b.setQuantity(1);
                i2b.setPrice(clean.getPrice());

                o2.setItems(Set.of(i2a, i2b));
                o2.setTotal(
                        i2a.getPrice() * i2a.getQuantity() +
                                i2b.getPrice() * i2b.getQuantity()
                );

                orders.save(o2);

                System.out.println("🛒 Orders de teste criadas com sucesso!");
            }

            if (coupons.count() == 0) {

                Coupon c1 = new Coupon();
                c1.setCode("X2");

                Coupon c2 = new Coupon();
                c2.setCode("X4");

                Coupon c3 = new Coupon();
                c3.setCode("X6");

                Coupon c4 = new Coupon();
                c4.setCode("X8");

                Coupon c5 = new Coupon();
                c5.setCode("X10");

                Coupon c6 = new Coupon();
                c6.setCode("Y5");

                Coupon c7 = new Coupon();
                c7.setCode("Y10");

                Coupon c8 = new Coupon();
                c8.setCode("Y15");

                Coupon c9 = new Coupon();
                c9.setCode("FLAT20");

                Coupon c10 = new Coupon();
                c10.setCode("FLAT50");


                coupons.saveAll(List.of(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10));
            }

            System.out.println("✅ Inicialização concluída!");
        };
    }
}
