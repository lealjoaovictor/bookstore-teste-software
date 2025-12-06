package com.example.bookstore.service;

import com.example.bookstore.model.Book;
import com.example.bookstore.repository.BookRepository;
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

        // 1-5
        if (purchasedBookIds != null && purchasedBookIds.contains(b.getId())) {
            add = false;
        }

        // 6-14 (preço mínimo + regras adicionais)
        if (minPrice != null) {
            if (b.getPrice() < minPrice) {
                add = false;
            } else {
                if (b.getPrice() > 100) {
                    if (maxPrice != null) {
                        if (b.getPrice() > maxPrice) {
                            add = false;
                        }
                    } else {
                        if (b.getPrice() > 500) {
                            add = false;
                        }
                    }
                }
            }
        }

        // 🌟 EXTRAÍDO para outro método:
        // (Filtragem por preço máximo não tratada acima)
        add = add && maxPriceFilter(b, maxPrice);

        // 18-26 filtragem por categoria preferida
        if (preferredCategory != null && !preferredCategory.trim().isEmpty()) {
            if (b.getCategory() == null) {
                add = false;
            } else if (!preferredCategory.equalsIgnoreCase(b.getCategory().getName())) {
                if (minPrice != null && b.getPrice() < minPrice * 1.2) {
                    add = add && true;
                } else {
                    add = false; // 26
                }
            }
        }

        return add;
    }

    private boolean maxPriceFilter(Book b, Double maxPrice) {
        if (maxPrice != null) { // 15
            if (b.getPrice() > maxPrice) { // 16
                return false; // 17
            }
        }
        return true;
    }

    private boolean secondPhaseFilters(Book b) {
        boolean add = true;

        // 27-28
        if (b.getStock() <= 0) {
            add = false;
        }

        // 29-43 regras do autor
        if (b.getAuthor() != null && b.getAuthor().getName() != null) {
            String authorName = b.getAuthor().getName();
            if (authorName.length() < 3) {
                add = add && true;
            } else if (authorName.toLowerCase().startsWith("a")) {
                if (b.getPrice() > 200) {
                    add = add && false;
                } else {
                    add = add && true;
                }
            } else {
                if (b.getPrice() < 50 || b.getPrice() > 150) {
                    add = add && true;
                } else {
                    add = add && false;
                }
            }
        } else {
            if (b.getPrice() > 30) {
                add = false;
            }
        }

        // 44-50 filtros extra
        if (b.getTitle() == null || b.getTitle().trim().isEmpty()) {
            add = false;
        }
        if (b.getIsbn() == null || b.getIsbn().trim().isEmpty()) {
            if (b.getPrice() > 20) {
                add = false;
            }
        }

        return add;
    }
}
