package com.example.udemyspring.security;

import java.security.Key;
import java.util.Date;
import java.util.Base64.Decoder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component // Annotation đánh dấu class JWTTokenProvider là một Spring bean và được quản lý
           // bởi Spring IoC container.
public class JWTTokenProvider {

    @Value("app.jwt-secret") // truy cập biến trong application.property
    private String jwtSecret;

    @Value("app-jwt-expiration-milliseconds") // truy cập biến trong application.property
    private long jwtExpirationDate;

    // generate JWT token method
    public String generateToken(Authentication authentication) {
        String username = authentication.getName();

        Date currentDate = new Date();

        // end date - thời gian hết hạn kể từ lúc tạo token
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(key())
                .compact();

        return token;
    }

    // vì hàm signWith nhận 1 Key mã hóa, phải tiến hành mã hóa khóa bí mật trước
    // khi truyền vào signWith
    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
}
