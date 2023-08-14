package com.example.udemyspring.service;

import com.example.udemyspring.DTO_payload.PostDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {
    PostDTO createPost(PostDTO postDTO);
    List<PostDTO> getAllPost(int pageIndex, int pageSize);
    PostDTO getPostById(Long id);

    PostDTO updatePost(PostDTO postDTO, Long id);

    void deletepost(Long id);

}
