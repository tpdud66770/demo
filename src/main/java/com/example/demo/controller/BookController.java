package com.example.demo.controller;

import com.example.demo.domain.Book;
import com.example.demo.domain.Likes;
import com.example.demo.dto.BookDTO;
import com.example.demo.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@CrossOrigin("*")
public class BookController {

    private final BookService bookService;

    // 도서 등록
    @PostMapping("/register")
    public Book create(@RequestBody Book book) {
        return bookService.save(book);
    }

    // 도서 목록 조회
    @GetMapping("/list")
    public List<BookDTO> list() {
        return bookService.findAll();
    }

    // 도서 상세 조회
    @GetMapping("/detail")
    public Book detail(@RequestParam Long id) {
        return bookService.detail(id);
    }

    // 도서 수정
    @PutMapping("/update")
    public Book update(@RequestBody Book book) {
        return bookService.update(book.getBookId(), book);
    }

    // 도서 삭제
    @DeleteMapping("/delete")
    public void delete(@RequestParam Long id) {
        bookService.delete(id);
    }

    //좋아요 클릭
    @PostMapping("/like")
    public void like(@RequestBody Likes like){
        bookService.likeToggle(like.getBook().getBookId(), like.getMember().getId());
    }
}

