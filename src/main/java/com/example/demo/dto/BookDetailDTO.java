package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class BookDetailDTO {

    private Long bookId;
    private Long memberId;
    private String title;
    private String content;
    private String author;
    private Long viewCnt;
    private Boolean liked;
    private LocalDate createAt;
    private LocalDate updateAt;

    private List<ImageDTO> images;
}
