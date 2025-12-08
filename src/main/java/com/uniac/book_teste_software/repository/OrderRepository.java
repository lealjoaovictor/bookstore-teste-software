package com.uniac.book_teste_software.repository;

import com.uniac.book_teste_software.model.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @EntityGraph(attributePaths = {
            "items",
            "items.book",
            "items.book.author"
    })
    List<Order> findAll();
}
