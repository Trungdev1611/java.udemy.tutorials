package com.example.udemyspring.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {// apply filter vào mỗi request

    private JWTTokenProvider jwtTokenProvider;
    private UserDetailsService userDetailsService;

    public JWTAuthenticationFilter(JWTTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // get token from http request Bearer header
        String token = getTokenFromRequest(request);

        if (token != null && !token.trim().isEmpty() && jwtTokenProvider.validateJwtToken(token)) {
            // get username from token nếu token thỏa mãn
            String username = jwtTokenProvider.getUsername(token);

            // load data from database dựa vào username
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        // tiếp tục thực hiện những thứ khác còn lại trong bộ lọc
        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization"); // đạt được bearer token trong request

        // validate bearer token
        if (bearerToken != null && !bearerToken.trim().isEmpty() && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length()); // lấy phần token sau chuỗi Bearer
        }
        return null; // null luôn thỏa mãn kiểu tham chiếu (ở đây là String)
    }

}
