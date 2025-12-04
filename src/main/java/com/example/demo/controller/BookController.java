package com.example.demo.controller;

import com.example.demo.domain.Book;
import com.example.demo.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")   //
@RequiredArgsConstructor
@CrossOrigin("*")
public class BookController {

    private final BookService bookService;

    // 전체 조회
    @GetMapping
    public List<Book> list() {
        return bookService.findAll();
    }

    // 건당 조회
    @GetMapping("/{id}")
    public Book detail(@PathVariable Long id) {
        return bookService.detail(id);
    }

    // 책 등록
    @PostMapping
    public Book create(@RequestBody Book book) {
        return bookService.save(book);
    }

    // 업데이트
    @PutMapping("/{id}")
    public Book update(@PathVariable Long id, @RequestBody Book book) {
        return bookService.update(id, book);
    }

    // 삭제
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }
}

