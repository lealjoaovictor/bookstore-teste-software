package com.uniac.book_teste_software.repository;

import com.uniac.book_teste_software.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

}