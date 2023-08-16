package com.example.udemyspring.service;

import com.example.udemyspring.DTO_payload.CommentDTO;

public interface CommentSerrvice {
    CommentDTO createComment(Long idAuthor, CommentDTO comment);

}
