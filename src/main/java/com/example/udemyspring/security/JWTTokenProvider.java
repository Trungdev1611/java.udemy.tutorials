package com.example.udemyspring.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Base64.Decoder;

import org.modelmapper.internal.asm.tree.UnsupportedClassVersionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.example.udemyspring.exception.ApiException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component // Annotation đánh dấu class JWTTokenProvider là một Spring bean và được quản lý
           // bởi Spring IoC container.
public class JWTTokenProvider {

    @Value("app.jwt-secret") // truy cập biến trong application.property
    private String jwtSecret;

    @Value("${app-jwt-expiration-milliseconds}") // truy cập biến trong application.property
    private Long jwtExpirationDate;

    // generate JWT token method
    public String generateToken(Authentication authentication) {
        String username = authentication.getName();

        System.out.println("generateToken::::" + username);
        Date currentDate = new Date();

        // end date - thời gian hết hạn kể từ lúc tạo token
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);

        String token = Jwts.builder()
                .setSubject(username) // set payload cho token là username, nếu muốn dùng bất kỳ dữ liệu nào khác
                                      // ngoài string trong setSubjectname thì ta có thể sử dụng .setClaims thay thế
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(key())
                .compact();

        // ví dụ về 1 cách tạo token với payload tùy chỉnh
        // String token = Jwts.builder()
        // .setClaims(new HashMap<String, Object>() {{
        // put("userId", userPrincipal.getUserId()); // Thêm thông tin tùy chỉnh vào
        // claims
        // put("role", userPrincipal.getRole()); // Ví dụ thêm vai trò của người dùng
        // // Thêm các thông tin khác tùy ý
        // }})
        // .setIssuedAt(new Date())
        // .setExpiration(expireDate)
        // .signWith(key())
        // .compact();

        return token;
    }

    // vì hàm signWith nhận 1 Key mã hóa, phải tiến hành mã hóa khóa bí mật trước
    // khi truyền vào signWith
    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    // get Username from token //giống như giải mã token để lấy username
    public String getUsername(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();
        String username = claims.getSubject();

        // ví dụ về cách giải mã token tùy chỉnh phía trên
        // Jws<Claims> claimsJws = Jwts.parserBuilder()
        // .setSigningKey(key())
        // .build()
        // .parseClaimsJws(token);

        // Claims claims = claimsJws.getBody();
        // String userId = (String) claims.get("userId"); // Lấy thông tin "userId" từ
        // claims
        // String role = (String) claims.get("role"); // Lấy thông tin "role" từ claims
        return username;
    }

    // validate jwt token
    public boolean validateJwtToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parse(token);
            return true;
        } catch (MalformedJwtException ex) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "InvalidJwt", 401);
        } catch (ExpiredJwtException ex) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Expired Jwt token", 401);
        } catch (UnsupportedJwtException ex) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Unsupport JWT token", 401);
        } catch (IllegalArgumentException ex) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "JWT claims string is empty", 401);
        }

    }
}
