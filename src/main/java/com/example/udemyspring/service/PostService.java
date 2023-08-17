package com.example.udemyspring.service;

import com.example.udemyspring.DTO_payload.PostDTO;
import com.example.udemyspring.DTO_payload.PostResponse;

import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {
    PostDTO createPost(PostDTO postDTO);

    PostResponse getAllPost(int pageIndex, int pageSize, String sortByField, String sortDirection);

    PostDTO getPostById(Long id);

    PostDTO updatePost(PostDTO postDTO, Long id);

    void deletepost(Long id);

}
