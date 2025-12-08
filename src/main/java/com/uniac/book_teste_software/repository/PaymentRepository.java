package com.uniac.book_teste_software.repository;

import com.uniac.book_teste_software.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}