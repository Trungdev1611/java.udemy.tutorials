package com.example.udemyspring.DTO_payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class JwtResponse {
    private String accessToken;
    private String tokenType = "Bearer";
}
