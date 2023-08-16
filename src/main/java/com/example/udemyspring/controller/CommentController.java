package com.example.udemyspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.udemyspring.DTO_payload.CommentDTO;
import com.example.udemyspring.service.CommentSerrvice;

@RestController
@RequestMapping("/api/posts")
public class CommentController {
    final private CommentSerrvice commentSerrvice;

    @Autowired
    public CommentController(CommentSerrvice commentSerrvice) {
        this.commentSerrvice = commentSerrvice;
    }

    @PostMapping("/{postId}/comments")
    // http://localhost:8080/api/posts/idPost/comments
    public CommentDTO createComment(@PathVariable Long postId, @RequestBody CommentDTO comment) {
        return commentSerrvice.createComment(postId, comment);
    }

}
