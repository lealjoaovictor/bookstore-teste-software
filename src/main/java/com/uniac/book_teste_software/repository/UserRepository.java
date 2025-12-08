package com.uniac.book_teste_software.repository;

import com.uniac.book_teste_software.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    
}