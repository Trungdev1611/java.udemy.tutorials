package com.example.udemyspring.service.impl;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.udemyspring.DTO_payload.CommentDTO;
import com.example.udemyspring.entity.Comment;
import com.example.udemyspring.entity.Post;
import com.example.udemyspring.exception.ApiException;
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
        Comment commentSave = commentRespository.save(comment);

        // chuyển comment thành commentDTO và gửi lại client
        CommentDTO newCommentDTO = convertCommentToCommentDTO(commentSave);

        // trả CommentDTO về client
        return newCommentDTO;
    }

    // get List comments theo postId
    @Override
    public List<CommentDTO> getCommentsByPostId(Long postId) {
        List<Comment> comments = commentRespository.findByPostId(postId);

        // convert comments List to commentDTO List
        return comments.stream().map(item -> convertCommentToCommentDTO(item))
                .collect(Collectors.toList());
    }

    // get comment chỉ cẩn commentId
    @Override
    public CommentDTO getCommentByCommentId(Long commentId) {
        Comment comment = commentRespository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
        return convertCommentToCommentDTO(comment);
    }

    // get comment cần cả commentId và PostId
    @Override
    public CommentDTO getCommentByCommentIdAndPostId(Long postId, Long commentId) {
        // tìm Post dựa trên idPost nếu khong thấy trả ra exception
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRespository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        // từ comment lấy ra postId so sánh với postId trong post
        // Lưu ý nếu như ta chỉ muốn dùng như thế này !comment.getPost().equals(post)
        // thì ta phải có một hàm hashcode bên trong lớp post
        if (!comment.getPost().getId().equals(post.getId())) {
            // bắt lỗi trên GlobalException
            throw new ApiException(HttpStatus.BAD_REQUEST, "comment không thuộc post", 400);

        }

        return convertCommentToCommentDTO(comment);
    }

    @Override
    public CommentDTO updateCommentByPostIdAndCommentId(Long postId, Long commentId, CommentDTO commentDTO) {
        // tìm Post dựa trên idPost nếu khong thấy trả ra exception
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRespository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if (!comment.getPost().getId().equals(post.getId())) {
            // bắt lỗi trên GlobalException
            throw new ApiException(HttpStatus.BAD_REQUEST, "comment không thuộc post", 400);

        }
        System.out.println("commentDTO" + commentDTO);
        comment.setName(commentDTO.getName());
        comment.setBody(commentDTO.getBody());
        comment.setEmail(commentDTO.getEmail());

        Comment updateComment = commentRespository.save(comment);
        return convertCommentToCommentDTO(updateComment);
    }

    @Override
    public void deleteCommentByPostIdAndCommentId(Long postId, Long commentId) {
        // tìm Post dựa trên idPost nếu khong thấy trả ra exception
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", postId));

        Comment comment = commentRespository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if (!comment.getPost().getId().equals(post.getId())) {
            // bắt lỗi trên GlobalException
            throw new ApiException(HttpStatus.BAD_REQUEST, "comment không tồn tại trong post", 400);

        }
        commentRespository.delete(comment);

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
