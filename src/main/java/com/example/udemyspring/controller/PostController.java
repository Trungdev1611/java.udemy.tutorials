package com.example.udemyspring.controller;

import com.example.udemyspring.DTO_payload.PostDTO;
import com.example.udemyspring.service.PostService;
import com.example.udemyspring.service.impl.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
   final  private PostService postService;

    @Autowired
    public PostController(PostService postService) { //đúng ra chỗ này phải là PostServiceImpl postService nhưng trong
        //trường hợp này chỉ có 1 triển khai duy nhất nên ta để PostService vẫn ok, nếu có 2 triển khai của interface vd ipml1 và impl2
        //thì ta phải chỉ rõ ràng ra PostController(PostServiceImpl1 postService) hoặc PostController(PostServiceImpl2 postService)
        this.postService = postService;

    }
    //create blog Post
    @PostMapping("/create_post")
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO) {
        return new ResponseEntity<>(postService.createPost(postDTO), HttpStatus.CREATED);
    }

    //getAllPost
    @GetMapping("/")
    //http://localhost:8080/api/posts/?pageIndex=0&pageSize=2
    public ResponseEntity<List<PostDTO>> getAllPost(
            //default require true, nếu không chỉ định value thì mặc định là page
            @RequestParam(value="pageIndex", defaultValue = "0", required = false) int pageIndex,
            @RequestParam(value="pageSize", defaultValue = "10", required = false) int pageSize
    ) {

        return new ResponseEntity<>(postService.getAllPost(pageIndex, pageSize), HttpStatus.OK);
    }

    //get Post by Id
    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id) {
        return new ResponseEntity<>(postService.getPostById(id), HttpStatus.OK);
    }

    //update Post
    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable(name = "id") Long idUpdate, @RequestBody PostDTO postDTO)  {
        return new ResponseEntity<>(postService.updatePost(postDTO, idUpdate), HttpStatus.OK);
    }
    //delete post
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        postService.deletepost(id);
        return new ResponseEntity<>("Delete post success", HttpStatus.OK);
    }

}
