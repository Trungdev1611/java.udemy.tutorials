package com.example.udemyspring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration // annotation này định nghĩa lớp cấu hình, định nghĩa 1 nguồn của các @Bean
@EnableMethodSecurity // cho phép áp dụng phân quyền hasRole ở bên controller
public class SercurityConfiguration {
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    } // Use BCryptPasswordEncoder
      // cấu hình bộ lọc sercurity

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable()) // disable csrf bảo vệ
                .authorizeHttpRequests((authorize) ->
                // authorize.anyRequest().authenticated()) // mọi request đều phải authen
                authorize.requestMatchers(HttpMethod.GET, "/api/**").permitAll() // bỏ qua việc đăng nhập với method get
                                                                                 // và bắt đầu với api in url
                        .anyRequest().authenticated())

                .httpBasic(Customizer.withDefaults()); // show popup login thay vì form như lúc trước, nếu trong postman
                                                       // thì ta gửi username và password qua header ở BasicAuth,
                                                       // username và password sẽ được gửi lên dưới dạng base64 với
                                                       // trường Authorization
        // tham khảo
        // https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/basic.html
        return http.build(); // return defaultSercurityFilterChain
    }

    // lưu trong memory này giống như là việc hardcode, chỉ có mục đích thử nghiệm
    // thôi
    // ở đây ta hardcode user1 có username là trung, pass là trung và roles là USER
    // ở đây ta hardcode admin có username là admin, admin là trung và roles là
    // ADMIN
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user1 = User.builder()
                .username("trung")
                .password(passwordEncoder().encode("trung"))
                .roles("USER")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();

        UserDetails agency = User.builder()
                .username("agency")
                .password(passwordEncoder().encode("agency"))
                .roles("AGENCY")
                .build();

        return new InMemoryUserDetailsManager(user1, admin, agency);
    }
}
