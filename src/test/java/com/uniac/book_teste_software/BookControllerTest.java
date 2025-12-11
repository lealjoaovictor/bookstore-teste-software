package com.uniac.book_teste_software;

import com.uniac.book_teste_software.controller.BookController;
import com.uniac.book_teste_software.model.Author;
import com.uniac.book_teste_software.model.Book;
import com.uniac.book_teste_software.model.Category;
import com.uniac.book_teste_software.repository.AuthorRepository;
import com.uniac.book_teste_software.repository.CategoryRepository;
import com.uniac.book_teste_software.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BookControllerTest {

    private BookController controller;
    private BookService bookService;
    private AuthorRepository authorRepository;
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setup() {
        bookService = Mockito.mock(BookService.class);
        authorRepository = Mockito.mock(AuthorRepository.class);
        categoryRepository = Mockito.mock(CategoryRepository.class);

        controller = new BookController(authorRepository, categoryRepository, bookService);
    }

    @Test
    void testAuthorNull() {
        Book b = new Book();

        Mockito.when(bookService.save(b)).thenReturn(b);

        Book saved = controller.create(b);
        assertSame(b, saved);
    }

    @Test
    void testAuthorNotFound() {
        Book b = new Book();
        Author a = new Author();
        a.setId(1L);
        b.setAuthor(a);

        Mockito.when(authorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> controller.create(b));
    }

    @Test
    void testCategoryNull() {
        Book b = new Book();

        Mockito.when(bookService.save(b)).thenReturn(b);

        Book saved = controller.create(b);
        assertSame(b, saved);
    }

    @Test
    void testCategoryNotFound() {
        Book b = new Book();
        Category c = new Category();
        c.setId(2L);
        b.setCategory(c);

        Mockito.when(categoryRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> controller.create(b));
    }

    @Test
    void testAuthorAndCategoryExist() {
        Book b = new Book();

        Author a = new Author();
        a.setId(1L);
        b.setAuthor(a);

        Category c = new Category();
        c.setId(2L);
        b.setCategory(c);

        Mockito.when(authorRepository.findById(1L)).thenReturn(Optional.of(a));
        Mockito.when(categoryRepository.findById(2L)).thenReturn(Optional.of(c));
        Mockito.when(bookService.save(b)).thenReturn(b);

        Book saved = controller.create(b);

        assertSame(a, saved.getAuthor());
        assertSame(c, saved.getCategory());
    }
}
