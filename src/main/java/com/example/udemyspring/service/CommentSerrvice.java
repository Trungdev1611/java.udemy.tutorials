package com.example.udemyspring.service;

import java.util.List;

import com.example.udemyspring.DTO_payload.CommentDTO;

public interface CommentSerrvice {
    CommentDTO createComment(Long idAuthor, CommentDTO comment);

    // get list comment by id post
    List<CommentDTO> getCommentsByPostId(Long postId);

    // get comment by id comment
    CommentDTO getCommentByCommentId(Long commentId);

    // get comment by idPost và idComment
    CommentDTO getCommentByCommentIdAndPostId(Long postId, Long commentId);

    //update comment by PostId and commentId
    CommentDTO updateCommentByPostIdAndCommentId(Long postId, Long commentId, CommentDTO commentDTO);

}
