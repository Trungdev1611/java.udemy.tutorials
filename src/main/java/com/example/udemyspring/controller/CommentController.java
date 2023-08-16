package com.example.udemyspring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<CommentDTO> createComment(@PathVariable Long postId, @RequestBody CommentDTO comment) {
        return new ResponseEntity<>(commentSerrvice.createComment(postId, comment), HttpStatus.OK);
    }

    @GetMapping("/{postId}/comments")
    // http://localhost:8080/api/posts/idPost/comments (get list comment theo id
    // Post)
    public ResponseEntity<List<CommentDTO>> getCommentsByPostId(@PathVariable Long postId) {
        return new ResponseEntity<>(commentSerrvice.getCommentsByPostId(postId), HttpStatus.OK);
    }

    @GetMapping("/comments/{commentId}")
    // http://localhost:8080/api/posts/comments/1 (get comment theo commentId)
    public ResponseEntity<CommentDTO> getCommentByCommentId(@PathVariable Long commentId) {
        return new ResponseEntity<>(commentSerrvice.getCommentByCommentId(commentId), HttpStatus.OK);
    }

    @GetMapping("{postId}/comments/{commentId}")
    // http://localhost:8080/api/posts/1/comments/1 (get comment theo commentId và
    // postId)
    public ResponseEntity<CommentDTO> getComentByCommentIdAndPostId(@PathVariable Long postId,
            @PathVariable Long commentId) {
        return new ResponseEntity<>(commentSerrvice.getCommentByCommentIdAndPostId(postId, commentId), HttpStatus.OK);
    }

    @PutMapping("{postId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> updateCommentByPostIdAndCommentId(@PathVariable Long postId,
                                                                        @PathVariable Long commentId,
                                                                        @RequestBody CommentDTO commentDTO) {
        return new ResponseEntity<>(commentSerrvice.updateCommentByPostIdAndCommentId(postId, commentId, commentDTO), HttpStatus.OK);
    }

}
