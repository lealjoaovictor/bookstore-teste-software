package com.uniac.book_teste_software;

import com.uniac.book_teste_software.model.Author;
import com.uniac.book_teste_software.model.Book;
import com.uniac.book_teste_software.model.Category;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BookTest {

    @Test
    void testSettersAndGettersBasicFields() {
        Book book = new Book();

        book.setId(1L);
        book.setTitle("Clean Code");
        book.setIsbn("CC-001");
        book.setPrice(89.90);
        book.setStock(10);

        assertEquals(1L, book.getId());
        assertEquals("Clean Code", book.getTitle());
        assertEquals("CC-001", book.getIsbn());
        assertEquals(89.90, book.getPrice());
        assertEquals(10, book.getStock());
    }

    @Test
    void testAssociationsAuthorAndCategory() {
        Book book = new Book();

        Author author = new Author();
        author.setId(5L);
        author.setName("Robert C. Martin");

        Category category = new Category();
        category.setId(3L);
        category.setName("Programming");

        book.setAuthor(author);
        book.setCategory(category);

        assertNotNull(book.getAuthor());
        assertEquals("Robert C. Martin", book.getAuthor().getName());

        assertNotNull(book.getCategory());
        assertEquals("Programming", book.getCategory().getName());
    }

    // ---------- OPCIONAL (CASO QUEIRA UM 3º TESTE NO RELATÓRIO) ----------
    @Test
    void testDefaultValuesForNewBook() {
        Book book = new Book();

        assertNull(book.getId());
        assertNull(book.getTitle());
        assertNull(book.getIsbn());
        assertEquals(0.0, book.getPrice());
        assertEquals(0, book.getStock());
        assertNull(book.getAuthor());
        assertNull(book.getCategory());
    }
}