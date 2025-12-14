package com.example.demo.controller;

import com.example.demo.dto.BookDTO;
import com.example.demo.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/main")
@CrossOrigin("*")
public class MainController {

    private final BookService bookService;

    // 메인 도서 목록
    @GetMapping
    public ResponseEntity<List<BookDTO>> list(
            @RequestAttribute(value = "loginId", required = false) String loginId
    ) {
        return ResponseEntity.ok(bookService.findAll(loginId));
    }

    // 메인 인기 도서
    @GetMapping("/hot")
    public ResponseEntity<List<BookDTO>> hotlist(
            @RequestAttribute(value = "loginId", required = false) String loginId
    ) {
        return ResponseEntity.ok(bookService.hotlist(loginId));
    }
}