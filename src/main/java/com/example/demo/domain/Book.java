package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "img_url" , columnDefinition = "CLOB")
    private String imgUrl;

    @OneToMany(mappedBy = "book", cascade = CascadeType.REMOVE)
    private List<Likes> likes = new ArrayList<>();

    @OneToMany(mappedBy = "book", cascade = CascadeType.REMOVE)
    private List<Comment> comment = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "member_loginId", referencedColumnName = "login_id")
    private Member member;
}
