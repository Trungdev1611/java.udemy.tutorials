package com.example.udemyspring.service;

import com.example.udemyspring.DTO_payload.PostDTO;

import java.util.List;

public interface PostService {
    PostDTO createPost(PostDTO postDTO);
    List<PostDTO> getAllPost();
    PostDTO getPostById(Long id);

    PostDTO updatePost(PostDTO postDTO, Long id);

    void deletepost(Long id);

}
