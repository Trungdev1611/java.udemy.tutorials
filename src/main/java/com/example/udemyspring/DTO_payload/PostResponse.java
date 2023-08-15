package com.example.udemyspring.DTO_payload;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private List<PostDTO> listPostDTO;
    private int pageIndex;
    private int pageSize;
    private Long totalElement;
    private int totalPage;
    private boolean lastPage;

}
