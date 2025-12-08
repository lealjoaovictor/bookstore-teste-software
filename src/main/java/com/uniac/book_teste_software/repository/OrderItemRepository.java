package com.uniac.book_teste_software.repository;

import com.uniac.book_teste_software.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}