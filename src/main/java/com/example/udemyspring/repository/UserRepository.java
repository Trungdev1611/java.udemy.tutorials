package com.example.udemyspring.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.udemyspring.entity.User;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    // các method query tạo trên tên thuộc tính
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameOrEmail(String username, String email);

    Boolean exitsByUsername(String username);

    Boolean exitsByEmail(String email);

}
