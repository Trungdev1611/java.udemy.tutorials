package com.example.udemyspring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.udemyspring.DTO_payload.CommentDTO;
import com.example.udemyspring.service.CommentSerrvice;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/posts")

@Tag(name = "CRUD REST API for Comment Resource")
public class CommentController {
    final private CommentSerrvice commentSerrvice;

    @Autowired
    public CommentController(CommentSerrvice commentSerrvice) {
        this.commentSerrvice = commentSerrvice;
    }

    @PostMapping("/{postId}/comments")
    // http://localhost:8080/api/posts/idPost/comments

    @SecurityRequirement(name = "Bearer Authentication") // name phải giống bên file cấu hình
    @Operation(summary = "create comment RestAPI", description = "create comment and save into databasee")
    @ApiResponse(responseCode = "200", description = "HttpStatus 200 created")

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
    // http://localhost:8080/api/posts/1/comments/1 (update comment theo comment id
    // và post id)

    @SecurityRequirement(name = "Bearer Authentication") // name phải giống bên file cấu hình
    @Operation(summary = "update comment RestAPI", description = "update comment and save into databasee")
    @ApiResponse(responseCode = "200", description = "HttpStatus 200 created")

    public ResponseEntity<CommentDTO> updateCommentByPostIdAndCommentId(@PathVariable Long postId,
            @PathVariable Long commentId,
            @RequestBody CommentDTO commentDTO) {
        return new ResponseEntity<>(commentSerrvice.updateCommentByPostIdAndCommentId(postId, commentId, commentDTO),
                HttpStatus.OK);
    }

    @DeleteMapping("{postId}/comments/{commentId}")
    // http://localhost:8080/api/posts/1/comments/2 (xóa comment thuộc post dựa trên
    // commentId và postId)

    @SecurityRequirement(name = "Bearer Authentication") // name phải giống bên file cấu hình
    @Operation(summary = "delete comment RestAPI", description = "delete comment and save into databasee")
    @ApiResponse(responseCode = "200", description = "HttpStatus 200 created")

    public ResponseEntity<String> deleteCommentByPostIdAndCommentId(@PathVariable Long postId,
            @PathVariable Long commentId) {
        commentSerrvice.deleteCommentByPostIdAndCommentId(postId, commentId);
        return new ResponseEntity<String>("Delete comment success", HttpStatus.OK);
    }

}
