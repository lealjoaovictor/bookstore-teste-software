package com.uniac.book_teste_software.controller;

import com.uniac.book_teste_software.model.Author;
import com.uniac.book_teste_software.repository.AuthorRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorRepository repo;

    public AuthorController(AuthorRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Author> list() {
        return repo.findAll();
    }
}