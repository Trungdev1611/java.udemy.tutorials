package com.example.udemyspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.udemyspring.entity.Comment;

public interface CommentRespository extends JpaRepository<Comment, Long> {

}
