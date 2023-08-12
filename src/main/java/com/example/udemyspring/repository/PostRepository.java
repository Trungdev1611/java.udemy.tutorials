package com.example.udemyspring.repository;

import com.example.udemyspring.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public abstract class PostRepository implements JpaRepository<Post, Long> {

}
