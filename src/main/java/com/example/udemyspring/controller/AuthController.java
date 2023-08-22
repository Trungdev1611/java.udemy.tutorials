package com.example.udemyspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.udemyspring.DTO_payload.LoginDTO;
import com.example.udemyspring.DTO_payload.RegisterDTO;
import com.example.udemyspring.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Build login restAPI
    @PostMapping({ "/login", "/signin" }) // có thể chọn 1 trong 2 url
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
        System.out.println(11111123);
        return new ResponseEntity<String>(authService.login(loginDTO), HttpStatus.OK);
    }

    // Build register restAPI
    @PostMapping({ "/register", "/signup" }) // có thể chọn 1 trong 2 url
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO) {
        System.out.println("run register controller");
        return new ResponseEntity<String>(authService.register(registerDTO), HttpStatus.CREATED);
    }
}
