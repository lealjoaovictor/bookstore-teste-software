package com.uniac.book_teste_software.service;


import com.uniac.book_teste_software.model.Book;
import com.uniac.book_teste_software.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

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
