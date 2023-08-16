package com.example.udemyspring.service.impl;

import org.springframework.stereotype.Service;

import com.example.udemyspring.DTO_payload.CommentDTO;
import com.example.udemyspring.entity.Comment;
import com.example.udemyspring.entity.Post;
import com.example.udemyspring.exception.ResourceNotFoundException;
import com.example.udemyspring.repository.CommentRespository;
import com.example.udemyspring.repository.PostRepository;
import com.example.udemyspring.service.CommentSerrvice;

@Service
public class CommentServiceImpl implements CommentSerrvice {
    PostRepository postRepository;
    CommentRespository commentRespository;

    public CommentServiceImpl(PostRepository postRepository, CommentRespository commentRespository) {
        this.postRepository = postRepository;
        this.commentRespository = commentRespository;
    }

    @Override
    public CommentDTO createComment(Long idPost, CommentDTO commentDTO) {
        // tìm Post dựa trên idPost nếu khong thấy trả ra exception
        Post post = postRepository.findById(idPost)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", idPost));

        // chuyển CommentDTO thành Comment
        Comment comment = convertCommentDTOToComment(commentDTO);
        comment.setPost(post);

        // save vào database
        commentRespository.save(comment);

        // chuyển comment thành commentDTO và gửi lại client
        CommentDTO newCommentDTO = convertCommentToCommentDTO(comment);

        // trả CommentDTO về client
        return newCommentDTO;
    }

    private Comment convertCommentDTOToComment(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setId(commentDTO.getId());
        comment.setName(commentDTO.getName());
        comment.setEmail(commentDTO.getEmail());
        comment.setBody(commentDTO.getBody());
        return comment;

    }

    private CommentDTO convertCommentToCommentDTO(Comment comment) {
        CommentDTO newCommentDTO = new CommentDTO();
        newCommentDTO.setId(comment.getId());
        newCommentDTO.setName(comment.getName());
        newCommentDTO.setEmail(comment.getEmail());
        newCommentDTO.setBody(comment.getBody());

        return newCommentDTO;
    }

}
