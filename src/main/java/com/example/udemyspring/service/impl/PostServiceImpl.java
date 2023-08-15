package com.example.udemyspring.service.impl;

import com.example.udemyspring.DTO_payload.PostDTO;
import com.example.udemyspring.DTO_payload.PostResponse;
import com.example.udemyspring.entity.Post;
import com.example.udemyspring.exception.ResourceNotFoundException;
import com.example.udemyspring.repository.PostRepository;
import com.example.udemyspring.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        // convert DTO to entity
        Post post = mapFromPostDTOToPost(postDTO);

        Post newPost = postRepository.save(post);
        // convert to entity to DTO
        return mapFromPostToPostDTO(newPost);
    }

    @Override
    public PostResponse getAllPost(int pageIndex, int pageSize, String sortByField, String sortDirection) {

        // lấy lớp pageable với 2 tham số là pageIndex và pageSize thì ta có thể làm thế
        // này (nếu chỉ cần phân trang)
        // Pageable pageable = PageRequest.of(pageIndex, pageSize);
        // Page<Post> postPage = postRepository.findAll(pageable);

        // C1
        // nếu như có sort ta có thể thêm vào tham số thứ 3 như sau (cả phân trang và
        // sort)
        Sort sort;
        if (sortDirection.equalsIgnoreCase("dsc")) {
            sort = Sort.by(sortByField).descending();
        } else {
            sort = Sort.by(sortByField).ascending();
        }

        Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);
        Page<Post> postPage = postRepository.findAll(pageable);

        // // C2 --xem lại có vẻ không đúng lắm
        // // cũng với trường hợp 3 tham số ta cũng có thể sử dụng như sau với việc khai
        // // báo lớp Sort riêng
        // Pageable pageable = PageRequest.of(pageIndex, pageSize);
        // Sort sort = Sort.by(sortByField).ascending(); // mặc định là ascending
        // Page<Post> postPage = postRepository.findAll(pageable, sort);

        // get Content from Page<Post>
        List<Post> ListOfPost = postPage.getContent();
        List<PostDTO> listPostDRO = ListOfPost.stream().map(item -> mapFromPostToPostDTO(item))
                .collect(Collectors.toList()); // Thu thập thành List<PostDTO>

        // response trả ra cho client là 1 object với 1 danh sách list DTO, pageIndex(vị
        // trí trang), pageSize(Số trang), totalElemt, totalPage...
        PostResponse postResponse = new PostResponse();

        postResponse.setListPostDTO(listPostDRO);
        postResponse.setPageIndex(postPage.getNumber()); // phương thức getNumber
                                                         // đã có sẵn trong lớp
                                                         // Page để láy page
                                                         // index hiện tại
        postResponse.setPageSize(postPage.getSize()); // phương thức getSize đã có sẵn trong lớp Page để láy số phần tử
                                                      // trên 1 trang
        postResponse.setTotalElement(postPage.getTotalElements()); // phương thức getTotalElements đã có sẵn trong lớp
                                                                   // page để lấy count tổng
        postResponse.setTotalPage(postPage.getTotalPages()); // phương thức getTotalPages đã có sẵn trong lớp page để
                                                             // lấy tổng số trang với pageSize tương ứng
        postResponse.setLastPage(postPage.isLast());
        return postResponse;
    }

    @Override
    public PostDTO getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return mapFromPostToPostDTO(post);
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        post.setTitle((postDTO.getTitle()));
        post.setDescription((postDTO.getDescription()));
        post.setContent((postDTO.getContent()));
        postRepository.save(post);
        return mapFromPostToPostDTO(post);
    }

    @Override
    public void deletepost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
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
