package com.dogus.notebook.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dogus.notebook.model.User;
import com.dogus.notebook.security.JwtProvider;
import com.dogus.notebook.service.UserService;
import com.dogus.notebook.web.response.AuthResponse;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired private UserService userService;
    @Autowired private AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<Void> create(@RequestBody User user) throws Exception {
        userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody User loginRequest) {
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        String token = JwtProvider.generateToken(username);
        AuthResponse authResponse = new AuthResponse();

        authResponse.setMessage("Login success");
        authResponse.setJwt(token);
        authResponse.setStatus(true);

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

}
