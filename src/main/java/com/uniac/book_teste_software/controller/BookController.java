package com.uniac.book_teste_software.controller;

import com.uniac.book_teste_software.model.Book;
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

    private final BookService bookService;

    public BookController(BookService bookService) {
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
        return bookService.save(book);
    }

    @PostMapping("/recommend")
    public List<Book> recommend(@RequestBody RecommendRequest req) {
        return bookService.recommend(
                req.purchasedBookIds,
                req.minPrice,
                req.maxPrice,
                req.preferredCategory
        );
    }

    public static class RecommendRequest {
        public java.util.List<Long> purchasedBookIds;
        public Double minPrice;
        public Double maxPrice;
        public String preferredCategory;
    }
}
