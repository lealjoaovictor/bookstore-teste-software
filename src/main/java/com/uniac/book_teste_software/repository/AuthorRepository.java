package com.uniac.book_teste_software.repository;

import com.uniac.book_teste_software.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository  extends JpaRepository<Author, Long> {
}
