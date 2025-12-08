package com.example.demo.controller;

import com.example.demo.domain.Book;
import com.example.demo.domain.Likes;
import com.example.demo.dto.BookDTO;
import com.example.demo.dto.CoverImageRequest;
import com.example.demo.service.BookService;
import com.example.demo.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;
    private final MemberService memberService;

    // 도서 등록
    @PostMapping
    public ResponseEntity<Book> create(@RequestBody Book book) {
        Book saved = bookService.save(book);
        return ResponseEntity.status(201).body(saved); // 201 Created
    }

    // 도서 목록 조회
    @GetMapping
    public ResponseEntity<List<BookDTO>> list() {
        return ResponseEntity.ok(bookService.findAll());
    }

    // 도서 인기조회순 조회
    @GetMapping("/hot")
    public ResponseEntity<List<BookDTO>> hotlist(){ return ResponseEntity.ok(bookService.hotlist());}

    // 도서 상세 조회
    @GetMapping("/{bookId}")
    public ResponseEntity<Book> detail(@PathVariable Long bookId) {
        Book book = bookService.detail(bookId);
        return ResponseEntity.ok(book);
    }

    // 도서 수정
    @PutMapping("/{bookId}")
    public ResponseEntity<Book> update(
            @PathVariable Long bookId,
            @RequestBody Book newData) {

        Book updated = bookService.update(bookId, newData);
        return ResponseEntity.ok(updated);
    }

    //좋아요 클릭
    @PatchMapping("/{bookId}")
    public ResponseEntity<?> like(@PathVariable Long bookId,@RequestAttribute("loginId") String loginId) {

        Long memberId = memberService.findIdByLoginId(loginId);

        boolean liked = bookService.likeToggle(bookId, memberId);

        if (liked) {
            return ResponseEntity.ok("liked");
        } else {
            return ResponseEntity.ok("unliked");
        }
    }

    // 좋아요 누른 도서 목록 조회
    @GetMapping("/liked")
    public ResponseEntity<List<BookDTO>> getLikedBooks(
            @RequestAttribute("loginId") String loginId) {

        Long memberId = memberService.findIdByLoginId(loginId); // 로그인한 사용자 ID 조회

        List<BookDTO> likedBooks = bookService.findLikedBooks(memberId);

        return ResponseEntity.ok(likedBooks);
    }

    // AI 생성 이미지 URL 저장
    @PutMapping("/{bookId}/cover-url")
    public ResponseEntity<?> updateBookCoverUrl(
            @PathVariable Long bookId,
            @RequestBody CoverImageRequest request) {

        Book updatedBook = bookService.updateBookCoverUrl(bookId, request.getImgUrl());
        return ResponseEntity.ok(updatedBook);
    }
}

