package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long bookId;
    private String title;
    private String content;
    private String author;

    @Column(name = "view_cnt")
    private Long viewCnt = 0L;

    @Column(name = "reg_time")
    private LocalDate regTime;

    @Column(name = "update_time")
    private LocalDate updateTime;

    @Column(name = "img_url")
    private String imgUrl;

    // 나중에 좋아요 기능 생기면 주석풀기
    //@OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    //@JsonManagedReference
    //private List<Like> likes = new ArrayList<>();
}
