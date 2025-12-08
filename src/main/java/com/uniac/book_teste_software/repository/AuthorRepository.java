package com.uniac.book_teste_software.repository;

import com.uniac.book_teste_software.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository  extends JpaRepository<Author, Long> {
}
