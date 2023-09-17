package com.example.udemyspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.udemyspring.DTO_payload.JwtResponse;
import com.example.udemyspring.DTO_payload.LoginDTO;
import com.example.udemyspring.DTO_payload.RegisterDTO;
import com.example.udemyspring.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/auth")

@Tag(name = "REST API cho đăng nhập lấy token và đăng ký")
public class AuthController {
    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Build login restAPI
    // @PostMapping({ "/login", "/signin" }) // có thể chọn 1 trong 2 url
    // public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
    // System.out.println(11111123);
    // return new ResponseEntity<String>(authService.login(loginDTO),
    // HttpStatus.OK);
    // }

    @Operation(summary = "Login", description = "login get token")
    @ApiResponse(responseCode = "200", description = "HttpStatus 200 Ok")

    @PostMapping({ "/login", "/signin" }) // có thể chọn 1 trong 2 url
    public ResponseEntity<JwtResponse> login(@RequestBody LoginDTO loginDTO) {

        String token = authService.login(loginDTO);

        JwtResponse jwtResponse = new JwtResponse();

        jwtResponse.setAccessToken(token);

        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }

    // Build register restAPI
    @PostMapping({ "/register", "/signup" }) // có thể chọn 1 trong 2 url

    @Operation(summary = "Register", description = "register account")
    @ApiResponse(responseCode = "200", description = "HttpStatus 200 Ok")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO) {
        System.out.println("run register controller");
        return new ResponseEntity<String>(authService.register(registerDTO), HttpStatus.CREATED);
    }
}
