package com.example.demo.controller;

import com.example.demo.domain.Book;
import com.example.demo.domain.Likes;
import com.example.demo.dto.BookDTO;
import com.example.demo.dto.CoverImageRequest;
import com.example.demo.service.BookService;
import com.example.demo.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
@CrossOrigin("*")
public class BookController {

    private final BookService bookService;
    private final MemberService memberService;

    // 도서 등록
    @PostMapping
    public ResponseEntity<Book> create(
            @RequestBody Book book ,
            @RequestAttribute("loginId") String loginId
    ) {
        return ResponseEntity.status(201).body(bookService.save(book , loginId)); // 201 Created
    }

    // 도서 목록 조회
    @GetMapping
    public ResponseEntity<List<BookDTO>> list(
            @RequestAttribute(value = "loginId" , required = false) String loginId
    ) {
        return ResponseEntity.ok(bookService.findAll(loginId));
    }

    // 도서 인기조회순 조회
    @GetMapping("/hot")
    public ResponseEntity<List<BookDTO>> hotlist(
            @RequestAttribute(value = "loginId" , required = false) String loginId
    ){
        return ResponseEntity.ok(bookService.hotlist(loginId));
    }

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
    public ResponseEntity<?> like(
            @PathVariable Long bookId,
            @RequestAttribute(value = "loginId" , required = false) String loginId) {

        // 비로그인 차단
        if (loginId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("로그인이 필요합니다.");
        }

        Long memberId = memberService.findIdByLoginId(loginId);
        boolean liked = bookService.likeToggle(bookId, memberId);

        return ResponseEntity.ok(liked); // true / false
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

