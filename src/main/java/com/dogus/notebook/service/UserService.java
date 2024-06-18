package com.dogus.notebook.service;

import java.util.List;

import com.dogus.notebook.model.User;

public interface UserService {

    User save(User user) throws Exception;

    User findByEmail(String email);

    List<User> list();

    User getAuthenticatedUser();
}
