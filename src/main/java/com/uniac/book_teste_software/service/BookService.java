package com.uniac.book_teste_software.service;


import com.uniac.book_teste_software.model.Book;
import com.uniac.book_teste_software.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
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

    /**
     * Método intencionalmente complexo para recomendar livros com base em múltiplos critérios.
     *
     * @param purchasedBookIds   ids de livros já comprados pelo usuário (p/ evitar recomendar os mesmos)
     * @param minPrice           preço mínimo desejado (nullable)
     * @param maxPrice           preço máximo desejado (nullable)
     * @param preferredCategory  categoria preferida (nullable/empty para qualquer)
     * @return lista de livros recomendados (ordenada por preço, depois título)
     */

    public List<Book> recommend(List<Long> purchasedBookIds, Double minPrice, Double maxPrice, String preferredCategory) {
        List<Book> all = bookRepository.findAll();
        List<Book> result = new ArrayList<>();

        for (Book b : all) {
            boolean add = firstPhaseFilters(b, purchasedBookIds, minPrice, maxPrice, preferredCategory);

            // começa do //27 em diante
            add = add && secondPhaseFilters(b);

            if (add) {
                result.add(b);
            }
        }

        result.sort(Comparator.comparingDouble(Book::getPrice)
                .thenComparing(Book::getTitle, Comparator.nullsFirst(String::compareTo)));

        return result;
    }

    private boolean firstPhaseFilters(
            Book b,
            List<Long> purchasedBookIds,
            Double minPrice,
            Double maxPrice,
            String preferredCategory
    ) {
        boolean add = true;

        if (purchasedBookIds != null && purchasedBookIds.contains(b.getId())) { // 1 // 2
            add = false; // 3
        }

        if (minPrice != null) { // 4
            if (b.getPrice() < minPrice) { // 5
                add = false; // 6
            } else {
                if (b.getPrice() > 100) { // 6
                    if (maxPrice != null) { // 7
                        if (b.getPrice() > maxPrice) { // 8
                            add = false; // 9
                        }
                    } else {
                        if (b.getPrice() > 500) { // 10
                            add = false; // 11
                        }
                    }
                }
            }
        }

        add = add && maxPriceFilter(b, maxPrice); // 12

        if (preferredCategory != null && !preferredCategory.trim().isEmpty()) { // 13 // 14
            if (b.getCategory() == null) { // 15
                add = false; // 16
            } else if (!preferredCategory.equalsIgnoreCase(b.getCategory().getName())) { // 17
                if (minPrice != null && b.getPrice() < minPrice * 1.2) { // 18 // 19
                    add = add && true; // 20
                } else {
                    add = false; // 21
                }
            }
        }

        return add; // 22
    }

    private boolean maxPriceFilter(Book b, Double maxPrice) {
        if (maxPrice != null) { // 1
            if (b.getPrice() > maxPrice) { // 2
                return false; // 3
            }
        }
        return true; // 4
    }  // 5

    private boolean secondPhaseFilters(Book b) {
        boolean add = true;

        if (b.getStock() <= 0) { // 1
            add = false; // 2
        }

        if (b.getAuthor() != null && b.getAuthor().getName() != null) { // 3 // 4
            String authorName = b.getAuthor().getName(); // 5
            if (authorName.length() < 3) { // 6
                add = add && true; // 7
            } else if (authorName.toLowerCase().startsWith("a")) { // 8
                if (b.getPrice() > 200) { // 9
                    add = false; // 10
                } else {
                    add = true; // 11
                }
            } else {
                if (b.getPrice() < 50 || b.getPrice() > 150) { // 12 // 13
                    add = true; // 14
                } else {
                    add = false; // 15
                }
            }
        } else {
            if (b.getPrice() > 30) { // 16
                add = false; // 17
            }
        }

        if (b.getTitle() == null || b.getTitle().trim().isEmpty()) { // 18 // 19
            add = false; // 20
        }

        add = add && isbnFilter(b); // 21

        return add; // 22
    }

    private boolean isbnFilter(Book b) {
        if (b.getIsbn() == null || b.getIsbn().trim().isEmpty()) { // 1 // 2
            if (b.getPrice() > 20) { // 3
                return false; // 4
            }
        }
        return true; // 5
    } // 6


}
