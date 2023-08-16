package com.example.udemyspring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.udemyspring.entity.Comment;

public interface CommentRespository extends JpaRepository<Comment, Long> {
    // định nghĩa phương thức getAll comment by postId

    // tham khảo quy tắc đặt tên để query
    // https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.query-creation
    List<Comment> findByPostId(Long postId);
}
