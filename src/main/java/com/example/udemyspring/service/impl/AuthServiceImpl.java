package com.example.udemyspring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.udemyspring.DTO_payload.LoginDTO;
import com.example.udemyspring.service.AuthService;
import org.springframework.security.core.AuthenticationException;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public String login(LoginDTO loginDto) {
        System.out.println("loginDTO1123!@#");
        // /Đối tượng authentication sẽ chứa thông tin xác thực sau khi quá trình xác
        // thực hoàn tất. //nếu xác thực không thành công thì 1 exception sẽ được ném ra
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        // đặt thông tin xác thực vào context bảo mật của Spring Security, để ứng dụng
        // biết rằng người dùng đã xác thực thành công.
        // Chẳng hạn, có thể sử dụng annotations như @Secured, @PreAuthorize,
        // @PostAuthorize để kiểm soát quyền truy cập vào các phương thức hoặc API trong
        // ứng dụng.
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // String email = authentication.getName(); // Lấy email
        // String password = (String) authentication.getCredentials(); // Lấy mật khẩu

        // System.out.println("email::" + email + "pass:::" + password);
        return "User login successfully";
    }
    // public String login(LoginDTO loginDto) {
    // try {
    // System.out.println("loginDTO1123!@#");
    // Authentication authentication = authenticationManager.authenticate(
    // new UsernamePasswordAuthenticationToken(
    // loginDto.getUsernameOrEmail(), loginDto.getPassword()));

    // SecurityContextHolder.getContext().setAuthentication(authentication);

    // return "User login successfully";
    // } catch (AuthenticationException e) {
    // // Xử lý lỗi xác thực ở đây
    // System.out.println("Authentication failed: " + e.getMessage());
    // return "Authentication failed: " + e;
    // }
    // }

}
