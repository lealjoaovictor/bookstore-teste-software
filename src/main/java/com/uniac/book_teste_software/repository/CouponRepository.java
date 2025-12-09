package com.uniac.book_teste_software.repository;

import com.uniac.book_teste_software.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    List<Coupon> findAllByCode(String code);
}
