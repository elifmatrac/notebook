package com.dogus.notebook.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dogus.notebook.repository.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
