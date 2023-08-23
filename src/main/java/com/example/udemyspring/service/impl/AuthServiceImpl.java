package com.example.udemyspring.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.udemyspring.DTO_payload.CommentDTO;
import com.example.udemyspring.DTO_payload.LoginDTO;
import com.example.udemyspring.DTO_payload.RegisterDTO;
import com.example.udemyspring.entity.Role;
import com.example.udemyspring.entity.User;
import com.example.udemyspring.exception.ApiException;
import com.example.udemyspring.repository.RoleRepository;
import com.example.udemyspring.repository.UserRepository;
import com.example.udemyspring.security.JWTTokenProvider;
import com.example.udemyspring.service.AuthService;
import org.springframework.security.core.AuthenticationException;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JWTTokenProvider jwtTokenProvider;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager,
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            JWTTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
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

        // create token
        String token = jwtTokenProvider.generateToken(authentication);

        return token;
        // return "User login successfully";
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
    @Override
    public String register(RegisterDTO registerDTO) {
        // add check username exit in database
        if (userRepository.existsByUsername(registerDTO.getUsername())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "username already exists in database ", 400);
        }
        if (userRepository.existsByEmail(registerDTO.getEmail())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Email already exists in database ", 400);
        }
        User user = new User();
        user.setName(registerDTO.getName());
        user.setEmail(registerDTO.getEmail());
        user.setUsername(registerDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        Set<Role> roles = new HashSet<>();

        // lấy dữ liệu của role user lên
        Role userRole = roleRepository.findByName("ROLE_USER").get(); // get này là method lấy giá trị optional

        System.out.println("userRole" + userRole.toString());
        // cho người dùng vừa tạo role user
        roles.add(userRole);
        user.setRoles(roles);

        // lưu vào hệ thống
        userRepository.save(user);
        return "Register success";
    }

}
