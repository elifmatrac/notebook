package com.dogus.notebook.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dogus.notebook.repository.UserRepository;
import com.dogus.notebook.repository.entity.User;
import com.dogus.notebook.service.UserService;
import com.dogus.notebook.service.dto.UserCreateInputDTO;
import com.dogus.notebook.service.dto.UserCreateOutputDTO;
import com.dogus.notebook.service.dto.UserGetOutputDTO;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserCreateOutputDTO save(UserCreateInputDTO input) throws Exception {
        String email = input.getEmail();
        String password = input.getPassword();

        Optional<UserGetOutputDTO> isEmailExist = findByEmail(email);
        if (isEmailExist.isPresent()) {
            throw new Exception("Email Is Already Used With Another Account");
        }

        User userToCreate = new User();
        userToCreate.setName(input.getName());
        userToCreate.setEmail(email);
        userToCreate.setPassword(passwordEncoder.encode(password));
        User createdUser = userRepository.save(userToCreate);

        UserCreateOutputDTO output = new UserCreateOutputDTO();
        output.setId(createdUser.getId());
        output.setName(createdUser.getName());
        output.setEmail(createdUser.getEmail());
        return output;
    }

    @Override
    public Optional<UserGetOutputDTO> findByEmail(String email) {

        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            UserGetOutputDTO output = new UserGetOutputDTO();
            output.setId(user.get().getId());
            output.setEmail(user.get().getEmail());
            output.setName(user.get().getName());
            return Optional.of(output);
        }
        return Optional.empty();
    }

    @Override
    public List<UserGetOutputDTO> list() {
        List<User> users = userRepository.findAll();
        List<UserGetOutputDTO> outputList = new ArrayList<>();
        users.stream().forEach(user -> {
            UserGetOutputDTO output = new UserGetOutputDTO();
            output.setId(user.getId());
            output.setEmail(user.getEmail());
            output.setName(user.getName());
            outputList.add(output);
        });
        return outputList;
    }

    @Override
    public Optional<UserGetOutputDTO> getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        return findByEmail(user.getUsername());
    }
}
