package com.example.udemyspring.DTO_payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class LoginDTO {
    private String usernameOrEmail; // đăng nhập qua username và password
    private String password;
}
