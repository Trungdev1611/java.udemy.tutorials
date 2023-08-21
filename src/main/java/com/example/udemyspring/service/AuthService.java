package com.example.udemyspring.service;

import com.example.udemyspring.DTO_payload.LoginDTO;

public interface AuthService { // define các method cho cả login and register
    String login(LoginDTO loginDTO);
}
