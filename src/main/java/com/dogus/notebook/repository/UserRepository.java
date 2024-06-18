package com.dogus.notebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dogus.notebook.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}
