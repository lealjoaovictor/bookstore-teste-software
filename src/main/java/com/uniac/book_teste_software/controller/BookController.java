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

    /**
     * Lista livros. Se fornecer ?q=texto faz busca por título contendo o texto (case-insensitive).
     */
    @GetMapping
    @Transactional
    public List<Book> list(@RequestParam(value = "q", required = false) String q) {
        return bookService.search(q);
    }

    /**
     * Endpoint para recomendações (opcional) — usa os mesmos parâmetros que BookService.recommend.
     * Exemplo: POST /api/books/recommend com corpo JSON:
     * {
     *   "purchasedBookIds": [1,2],
     *   "minPrice": 10.0,
     *   "maxPrice": 100.0,
     *   "preferredCategory": "Fiction"
     * }
     */
    @PostMapping("/recommend")
    public List<Book> recommend(@RequestBody RecommendRequest req) {
        return bookService.recommend(req.purchasedBookIds, req.minPrice, req.maxPrice, req.preferredCategory);
    }

    public static class RecommendRequest {
        public java.util.List<Long> purchasedBookIds;
        public Double minPrice;
        public Double maxPrice;
        public String preferredCategory;
    }
}
