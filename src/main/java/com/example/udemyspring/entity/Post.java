package com.example.udemyspring.entity;

import java.util.Set;

import jakarta.persistence.*;
import lombok.*;

//Do @Data bao gồm toString method nên có thể dẫn đến loop vô tận với modelMapper
//ta sẽ sử dụng @Getter @Setter trong trường hợp này
// @Data
@Getter
@Setter
/*
 * Annotation @Data tương đương với 1 nhóm annotation sau
 *
 * @Getter: Tự động tạo getter cho các thuộc tính.
 *
 * @Setter: Tự động tạo setter cho các thuộc tính không phải final.
 *
 * @ToString: Tự động tạo phương thức toString().
 *
 * @EqualsAndHashCode: Tự động tạo phương thức equals() và hashCode()
 *
 * @RequiredArgsConstructor: Tự động tạo constructor với các tham số được đánh
 * dấu là final hoặc được đánh dấu bằng @NonNull
 */
@AllArgsConstructor // tạo constructor có tất cả các đối số cho class
@NoArgsConstructor // tạo constructor không đối số cho class = public Post() {}
@Entity

// anotation chỉ định tên table cụ thể thay vì lấy theo tên class, thuộc tính
// uniqueConstraints dùng để định danh các giá trị trong cột là duy nhất
@Table(name = "post", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "title" })
})
public class Post {
    @Id // chỉ định primarykey
    @GeneratedValue(strategy = GenerationType.IDENTITY) // chỉ định cách tạo primarykey - ở đây là tự tăng

    private Long id;

    // có thể chỉ định tên cột trong table - mặc định là tên cột = tên field
    @Column(name = "title", nullable = false) // không cho phép null
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String content;

    @OneToMany(mappedBy = "post")
    private Set<Comment> comments; // một tập hợp các comment không trùng nhau

}
