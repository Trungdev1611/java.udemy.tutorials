package com.example.udemyspring.controller;

import com.example.udemyspring.DTO_payload.PostDTO;
import com.example.udemyspring.DTO_payload.PostResponse;
import com.example.udemyspring.service.PostService;
import com.example.udemyspring.service.impl.PostServiceImpl;
import com.example.udemyspring.utils.AppConstant;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    final private PostService postService;

    @Autowired
    public PostController(PostService postService) { // đúng ra chỗ này phải là PostServiceImpl postService nhưng trong
        // trường hợp này chỉ có 1 triển khai duy nhất nên ta để PostService vẫn ok, nếu
        // có 2 triển khai của interface vd ipml1 và impl2
        // thì ta phải chỉ rõ ràng ra PostController(PostServiceImpl1 postService) hoặc
        // PostController(PostServiceImpl2 postService)
        this.postService = postService;
    }

    // create blog Post
    @PreAuthorize("hasRole('ADMIN')") // chỉ định ADMIN mới có quyền thực hiện chức năng này
    @PostMapping("/create_post")

    // chỉ định annotation để require token trên swagger
    @SecurityRequirement(name = "Bearer Authentication") // name phải giống bên file cấu hình
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO) {
        return new ResponseEntity<>(postService.createPost(postDTO), HttpStatus.CREATED);
    }

    // getAllPost
    @GetMapping("")
    // http://localhost:8080/api/posts?pageIndex=0&pageSize=2
    // http://localhost:8080/api/posts?pageIndex=0&pageSize=1000&sortByField=title
    // http://localhost:8080/api/posts?pageIndex=0&pageSize=1000&sortByField=content&sortDirection=dsc
    public ResponseEntity<PostResponse> getAllPost(
            // default require true, nếu không chỉ định value thì mặc định là page
            @RequestParam(value = "pageIndex", defaultValue = AppConstant.PAGE_INDEX_DEFAULT, required = false) int pageIndex,
            @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE_DEFAULT, required = false) int pageSize,
            @RequestParam(value = "sortByField", defaultValue = AppConstant.SORT_BY_FIELD_DEFAULT, required = false) String sortByField,
            @RequestParam(value = "sortDirection", defaultValue = AppConstant.SORT_DIRECTION_DEFAULT, required = false) String sortDirection) {

        return new ResponseEntity<>(postService.getAllPost(pageIndex, pageSize, sortByField, sortDirection),
                HttpStatus.OK);
    }

    // get Post by Id

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id) {
        return new ResponseEntity<>(postService.getPostById(id), HttpStatus.OK);
    }

    // update Post
    @PreAuthorize("hasRole('ADMIN')") // chỉ định ADMIN mới có quyền thực hiện chức năng này
    @PutMapping("/{id}")

    // chỉ định annotation để require token trên swagger
    @SecurityRequirement(name = "Bearer Authentication") // name phải giống bên file cấu hình
    public ResponseEntity<PostDTO> updatePost(@PathVariable(name = "id") Long idUpdate, @RequestBody PostDTO postDTO) {
        return new ResponseEntity<>(postService.updatePost(postDTO, idUpdate), HttpStatus.OK);
    }

    // delete post
    @PreAuthorize("hasRole('AGENCY')") // chỉ định ADMIN mới có quyền thực hiện chức năng này
    @DeleteMapping("/{id}")

    // chỉ định annotation để require token trên swagger
    @SecurityRequirement(name = "Bearer Authentication") // name phải giống bên file cấu hình
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        postService.deletepost(id);
        return new ResponseEntity<>("Delete post success", HttpStatus.OK);
    }

}
