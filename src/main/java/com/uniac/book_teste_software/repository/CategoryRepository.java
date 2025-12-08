package com.uniac.book_teste_software.repository;

import com.uniac.book_teste_software.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}