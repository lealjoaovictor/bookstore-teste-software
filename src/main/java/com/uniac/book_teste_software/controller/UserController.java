package com.uniac.book_teste_software.controller;

import com.uniac.book_teste_software.model.User;
import com.uniac.book_teste_software.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository repo;

    public UserController(UserRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<User> list() {
        return repo.findAll();
    }

    @PostMapping
    public User create(@RequestBody User user) {
        return repo.save(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
