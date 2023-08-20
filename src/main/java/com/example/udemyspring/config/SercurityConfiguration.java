package com.example.udemyspring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // annotation này định nghĩa lớp cấu hình, định nghĩa 1 nguồn của các @Bean
public class SercurityConfiguration {

    // cấu hình bộ lọc sercurity
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable()) // disable csrf bảo vệ
                .authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated()) // mọi request đều phải
                                                                                              // authen
                .httpBasic(Customizer.withDefaults()); // show popup login thay vì form như lúc trước, nếu trong postman
                                                       // thì ta gửi username và password qua header ở BasicAuth,
                                                       // username và password sẽ được gửi lên dưới dạng base64 với
                                                       // trường Authorization
        // tham khảo
        // https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/basic.html
        return http.build(); // return defaultSercurityFilterChain
    }
}
