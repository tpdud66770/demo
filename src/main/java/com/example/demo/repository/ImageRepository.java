package com.example.demo.repository;

import com.example.demo.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image , Long> {

    // 특정 도서의 이미지 목록 조회
    List<Image> findByBookBookId(Long bookId);

}
