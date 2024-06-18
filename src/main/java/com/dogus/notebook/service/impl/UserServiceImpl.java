package com.dogus.notebook.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dogus.notebook.model.User;
import com.dogus.notebook.repository.UserRepository;
import com.dogus.notebook.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User save(User user) throws Exception {
        String email = user.getEmail();
        String password = user.getPassword();

        User isEmailExist = findByEmail(email);
        if (isEmailExist != null) {
            throw new Exception("Email Is Already Used With Another Account");
        }

        User userToCreate = new User();
        userToCreate.setName(user.getName());
        userToCreate.setEmail(email);
        userToCreate.setPassword(passwordEncoder.encode(password));
        return userRepository.save(userToCreate);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> list() {
        return userRepository.findAll();
    }

    @Override
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        return findByEmail(user.getUsername());
    }
}
