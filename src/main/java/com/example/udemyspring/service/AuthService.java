package com.example.udemyspring.service;

import com.example.udemyspring.DTO_payload.LoginDTO;
import com.example.udemyspring.DTO_payload.RegisterDTO;

public interface AuthService { // define các method cho cả login and register
    String login(LoginDTO loginDTO);

    String register(RegisterDTO registerDTO);
}
