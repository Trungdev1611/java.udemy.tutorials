//DTO là chỉ việc chuyển đổi dữ liệu (lọc các dữ liệu nào client cần thì gửi thôi chứ không gửi hết). Điều này ta hoàn toàn có thể
//sử dụng  1 method trong class để lấy các trường cần thiết hoặc sử dụng hashMap để put các trường đó vào hashMap và gửi
//về client nhưng việc sử dụng DTO là lựa chọn tốt và được sử dụng trong thực tế (thực tế thì cũng ít ông lọc dữ liệu mà gửi lắm-trừ trường
//hợp dữ liệu gửi về client quá nhiều
package com.example.udemyspring.DTO_payload;

import java.util.Set;

import lombok.Data;

@Data
public class PostDTO {
    private Long id;
    private String title;
    private String description;
    private String content;

    // danh sách comment trong pôst
    Set<CommentDTO> comments; // trường này phải trùng với @OneToMany(mappedBy = "post") private Set<Comment>
                              // comments; trong lớp post

}
