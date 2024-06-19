package com.dogus.notebook.service;

import java.util.List;
import java.util.Optional;

import com.dogus.notebook.service.dto.UserCreateInputDTO;
import com.dogus.notebook.service.dto.UserCreateOutputDTO;
import com.dogus.notebook.service.dto.UserGetOutputDTO;

public interface UserService {

    UserCreateOutputDTO save(UserCreateInputDTO input) throws Exception;

    Optional<UserGetOutputDTO> findByEmail(String email);

    List<UserGetOutputDTO> list();

    Optional<UserGetOutputDTO> getAuthenticatedUser();
}
