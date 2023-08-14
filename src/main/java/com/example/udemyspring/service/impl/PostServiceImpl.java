package com.example.udemyspring.service.impl;

import com.example.udemyspring.DTO_payload.PostDTO;
import com.example.udemyspring.entity.Post;
import com.example.udemyspring.exception.ResourceNotFoundException;
import com.example.udemyspring.repository.PostRepository;
import com.example.udemyspring.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {

        this.postRepository = postRepository;
    }

    @Override
    public PostDTO createPost(PostDTO postDTO) {
        //convert DTO to entity
       Post post =  mapFromPostDTOToPost(postDTO);

        Post newPost = postRepository.save(post);
        //convert to entity to DTO
        return mapFromPostToPostDTO(newPost);
    }

    @Override
    public List<PostDTO> getAllPost() {
        return postRepository.findAll().stream().map(item -> mapFromPostToPostDTO(item)).collect(Collectors.toList()); // Thu thập thành List<PostDTO>
    }

    @Override
    public PostDTO getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post","id", id));
        return mapFromPostToPostDTO(post);
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post","id", id));
        post.setTitle((postDTO.getTitle()));
        post.setDescription((postDTO.getDescription()));
        post.setContent((postDTO.getContent()));
        postRepository.save(post);
        return mapFromPostToPostDTO(post);
    }

    @Override
    public void deletepost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post","id", id));
        postRepository.delete(post);

    }

    private PostDTO mapFromPostToPostDTO(Post post) {
        PostDTO postDTOCreated = new PostDTO();
        postDTOCreated.setId(post.getId());
        postDTOCreated.setTitle(post.getTitle());
        postDTOCreated.setDescription(post.getDescription());
        postDTOCreated.setContent(post.getContent());
        return postDTOCreated;
    }

    private Post mapFromPostDTOToPost(PostDTO postDTO) {
        Post postCreated = new Post();
        postCreated.setId((postDTO.getId()));
        postCreated.setTitle((postDTO.getTitle()));
        postCreated.setDescription((postDTO.getDescription()));
        postCreated.setContent((postDTO.getContent()));
        return postCreated;
    }

}
