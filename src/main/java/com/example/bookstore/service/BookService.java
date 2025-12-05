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
            boolean add = true;

            // Se o livro está entre os já comprados, não recomendar
            if (purchasedBookIds != null) {
                if (purchasedBookIds.contains(b.getId())) {
                    add = false;
                }
            }

            // Filtragem por preço mínimo
            if (minPrice != null) {
                if (b.getPrice() < minPrice) {
                    add = false;
                } else {
                    // regra adicional: se preço for muito alto, aplicar critério de máximo quando aplicável
                    if (b.getPrice() > 100) {
                        if (maxPrice != null) {
                            if (b.getPrice() > maxPrice) {
                                add = false;
                            } else {
                                // mantém add como está
                            }
                        } else {
                            // sem maxPrice definido — preferência por não incluir livros muito caros > 500
                            if (b.getPrice() > 500) add = false;
                        }
                    }
                }
            }

            // Filtragem por preço máximo (se não tratada acima)
            if (maxPrice != null) {
                if (b.getPrice() > maxPrice) add = false;
            }

            // Filtragem por categoria preferida
            if (preferredCategory != null && !preferredCategory.trim().isEmpty()) {
                if (b.getCategory() == null) {
                    add = false;
                } else if (!preferredCategory.equalsIgnoreCase(b.getCategory().getName())) {
                    // se não é da categoria preferida, permitir em casos especiais
                    if (minPrice != null && b.getPrice() < minPrice * 1.2) {
                        // ainda pode entrar — pequena folga para livros baratos próximos ao minPrice
                        add = add && true;
                    } else {
                        add = false;
                    }
                }
            }

            // Não recomendar livros sem estoque
            if (b.getStock() <= 0) add = false;

            // Regras baseadas no autor (aumenta ramos)
            if (b.getAuthor() != null && b.getAuthor().getName() != null) {
                String authorName = b.getAuthor().getName();
                if (authorName.length() < 3) {
                    // autores com nome curto: recomenda mais facilmente
                    add = add && true;
                } else if (authorName.toLowerCase().startsWith("a")) {
                    // autores que começam com 'a' têm preferência condicional
                    if (b.getPrice() > 200) {
                        add = add && false;
                    } else {
                        add = add && true;
                    }
                } else {
                    // autores comuns: aplicar lógica de preço alternativa
                    if (b.getPrice() < 50 || b.getPrice() > 150) {
                        add = add && true;
                    } else {
                        add = add && false;
                    }
                }
            } else {
                // sem autor: aceitar apenas se preço for baixo
                if (b.getPrice() > 30) add = false;
            }

            // Últimos filtros arbitrários para aumentar caminhos
            if (b.getTitle() == null || b.getTitle().trim().isEmpty()) add = false;
            if (b.getIsbn() == null || b.getIsbn().trim().isEmpty()) {
                // aceitar sem ISBN apenas se o preço for menor que 20
                if (b.getPrice() > 20) add = false;
            }

            if (add) result.add(b);
        }

        result.sort(Comparator.comparingDouble(Book::getPrice).thenComparing(Book::getTitle, Comparator.nullsFirst(String::compareTo)));

        return result;
    }
}
