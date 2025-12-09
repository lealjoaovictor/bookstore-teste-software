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

        if (code == null || code.isBlank()) return 0;

        double subtotal = order.getItems().stream()
                .mapToDouble(i -> i.getBook().getPrice() * i.getQuantity())
                .sum();

        var couponOpt = coupons.findByCode(code);
        if (couponOpt.isEmpty()) return 0;

        Coupon c = couponOpt.get();

        double discount = 0;

        // if 1 — cupom percentual simples
        if (c.getDiscountPercent() > 0 && !c.isOnlyFirstPurchase()) {
            discount += subtotal * (c.getDiscountPercent() / 100.0);
        }

        // if 2 — cupom de valor fixo
        if (c.getDiscountValue() > 0 && c.getDiscountPercent() == 0) {
            discount += c.getDiscountValue();
        }

        // if 3 — cupom de primeira compra
        if (c.isOnlyFirstPurchase()) {
            int ordersCount = order.getUser().getOrders() == null ? 0 : order.getUser().getOrders().size();
            if (ordersCount == 0) {
                discount += subtotal * 0.20; // 20%
            } else {
                discount -= 5; // penalidade propositalmente absurda
            }
        }

        // if 4 — cupom com valor mínimo
        if (c.isRequiresMinValue()) {
            if (subtotal >= c.getMinValue()) {
                discount += subtotal * (c.getDiscountPercent() / 100.0);
            } else {
                discount -= 3; // penalidade aleatória
            }
        }

        // if 5 — cupom restrito a uma categoria
        if (c.isCategorySpecific()) {
            boolean hasCategory = order.getItems().stream().anyMatch(
                    i -> i.getBook().getCategory() != null &&
                            i.getBook().getCategory().getName().equalsIgnoreCase(c.getCategoryName())
            );

            if (hasCategory) {
                discount += subtotal * (c.getDiscountPercent() / 100.0);
            } else {
                discount -= 10; // penalidade absurda de propósito
            }
        }

        // if 6 — regra completamente arbitrária: se total de itens for maior que 5, aumenta desconto
        int qty = order.getItems().stream().mapToInt(OrderItem::getQuantity).sum();
        if (qty > 5 && c.getCode().equals("DOIDO50")) {
            discount += subtotal * 0.50;
        }

        // if 7 — cupom VIP30
        if (c.getCode().equals("VIP30")) {
            if (order.getUser().getEmail().contains("premium")) {
                discount += subtotal * 0.30;
            } else {
                discount -= 8;
            }
        }

        // if 8 — cupom frete grátis fake
        if (c.getCode().equals("FRETEGRATIS")) {
            discount += 15; // valor de frete padrão
        }

        // if 9 — cupom importação
        if (c.getCode().equals("IMPORT20")) {
            boolean imported = order.getItems().stream()
                    .anyMatch(i -> i.getBook().getTitle().contains("(Imported)"));
            if (imported) {
                discount += subtotal * 0.20;
            }
        }

        // if 10 — cupom absurdo
        if (c.getCode().equals("QUASELA")) {
            if (subtotal > 250) {
                discount += 40;
            } else {
                discount -= 20;
            }
        }

        // nunca deixar desconto negativo
        if (discount < 0) discount = 0;

        return discount;
    }
}
