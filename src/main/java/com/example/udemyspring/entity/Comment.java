package com.example.udemyspring.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comment")

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String name;
    private String email;
    private String body;

    // Many comment belong to one Post
    @ManyToOne(fetch = FetchType.LAZY) // FetchType.LAZY là mặc định, nó sẽ
    /*
     * FetchType.LAZY: Đây là chế độ mặc định. Khi sử dụng chế độ này, dữ liệu từ
     * mối quan hệ @ManyToOne sẽ chỉ được truy vấn từ cơ sở dữ liệu khi thực sự cần
     * đến (lười load). Điều này có thể giúp tối ưu hóa hiệu suất nếu không phải lúc
     * nào bạn cũng cần dữ liệu liên quan.
     * FetchType.EAGER: Khi sử dụng chế độ này, dữ liệu từ mối quan hệ @ManyToOne sẽ
     * được truy vấn từ cơ sở dữ liệu ngay khi entity chứa mối quan hệ này được truy
     * vấn. Điều này có thể dẫn đến việc truy vấn không cần thiết và làm tăng thời
     * gian và tài nguyên truy vấn.
     */
    @JoinColumn(name = "post_id", nullable = false) // chỉ định khóa ngoại giữa bảng Post và bảng comment
    private Post post;

}
