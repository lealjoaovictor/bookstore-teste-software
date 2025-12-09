package com.uniac.book_teste_software.service;


import com.uniac.book_teste_software.model.Book;
import com.uniac.book_teste_software.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Serviço para operações relacionadas a livros.
 */
@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Busca livros por título (contendo q). Se q for nulo ou vazio, retorna todos.
     */
    public List<Book> search(String q) {
        if (q == null || q.trim().isEmpty()) {
            return bookRepository.findAll();
        }
        return bookRepository.findByTitleContainingIgnoreCase(q);
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

}
