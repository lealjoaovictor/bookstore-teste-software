package com.uniac.book_teste_software.controller;

import com.uniac.book_teste_software.model.Book;
import com.uniac.book_teste_software.repository.AuthorRepository;
import com.uniac.book_teste_software.repository.CategoryRepository;
import com.uniac.book_teste_software.service.BookService;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Endpoints para operações simples com livros.
 */
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;

    private final BookService bookService;

    public BookController(AuthorRepository authorRepository, CategoryRepository categoryRepository, BookService bookService) {
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
        this.bookService = bookService;
    }

    @GetMapping
    @Transactional
    public List<Book> list(@RequestParam(value = "q", required = false) String q) {
        return bookService.search(q);
    }

    @PostMapping
    @Transactional
    public Book create(@RequestBody Book book) {

        if (book.getAuthor() != null) {
            var author = authorRepository.findById(book.getAuthor().getId())
                    .orElseThrow();
            book.setAuthor(author);
        }

        if (book.getCategory() != null) {
            var category = categoryRepository.findById(book.getCategory().getId())
                    .orElseThrow();
            book.setCategory(category);
        }

        return bookService.save(book);
    }

}
