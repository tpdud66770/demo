package com.example.demo.controller;

import com.example.demo.dto.BookDTO;
import com.example.demo.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/main")
@CrossOrigin("*")
public class MainController {

    private final BookService bookService;

    // 도서 목록 조회
    @GetMapping
    public ResponseEntity<List<BookDTO>> list() {
        return ResponseEntity.ok(bookService.findAll());
    }

    // 도서 인기조회순 조회
    @GetMapping("/hot")
    public ResponseEntity<List<BookDTO>> hotlist(){ return ResponseEntity.ok(bookService.hotlist());}
}
