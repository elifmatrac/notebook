package com.dogus.notebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dogus.notebook.controller.request.RequestCreateUser;
import com.dogus.notebook.controller.request.RequestSignInUser;
import com.dogus.notebook.controller.response.ResponseCreateUser;
import com.dogus.notebook.controller.response.ResponseSignInUser;
import com.dogus.notebook.security.JwtProvider;
import com.dogus.notebook.service.UserService;
import com.dogus.notebook.service.dto.UserCreateInputDTO;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired private UserService userService;
    @Autowired private AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<ResponseCreateUser> create(@RequestBody RequestCreateUser request) throws Exception {
        UserCreateInputDTO inputDTO = new UserCreateInputDTO();
        inputDTO.setEmail(request.getEmail());
        inputDTO.setPassword(request.getPassword());
        userService.save(inputDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/signin")
    public ResponseEntity<ResponseSignInUser> signin(@RequestBody RequestSignInUser request) {
        String username = request.getEmail();
        String password = request.getPassword();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        String token = JwtProvider.generateToken(username);
        ResponseSignInUser response = new ResponseSignInUser();
        response.setJwt(token);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
