package com.example.demo.controller;

import com.example.demo.domain.Image;
import com.example.demo.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ImageController {

    private final ImageService imageService;

    // 이미지 등록
    @PostMapping("/register")
    public Image register(@RequestParam Long bookId,
                          @RequestParam String imgUrl) {
        return imageService.saveImage(bookId , imgUrl);
    }

    // 특정 책의 모든 이미지 조회
    @GetMapping("/list")
    public List<Image> list(@RequestParam Long bookId) {
        return imageService.findByBook(bookId);
    }

    // 이미지 삭제
    @DeleteMapping("/delete")
    public void delete(@RequestParam Long imageId) {
        imageService.delete(imageId);
    }
}
