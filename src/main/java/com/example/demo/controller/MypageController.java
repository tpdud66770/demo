package com.example.demo.controller;

import com.example.demo.domain.Member;
import com.example.demo.dto.BookDTO;
import com.example.demo.dto.MessageDTO;
import com.example.demo.service.BookService;
import com.example.demo.service.MemberService;
import com.example.demo.service.MypageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
@CrossOrigin("*")
@Log4j2
public class MypageController {

    private final MypageService mypageService;
    private final BookService bookService;
    private final MemberService memberService;

    // 내가 등록한 도서 목록 조회
    @GetMapping
    public ResponseEntity<List<BookDTO>> register(HttpServletRequest request){

        log.info("내가 등록한 도서 목록 조회 확인");

        String loginId = (String) request.getAttribute("loginId");

        log.info("login Id : "+loginId);

        List<BookDTO> list = mypageService.findRegisteredBook(loginId);

        log.info("찾은 책 목록들");

        return ResponseEntity.ok(mypageService.findRegisteredBook(loginId));
    }

    // 도서 삭제
    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> delete(@PathVariable Long bookId) {
        bookService.delete(bookId);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    // 좋아요 누른 도서 목록 조회
    @GetMapping("/liked")
    public ResponseEntity<List<BookDTO>> getLikedBooks(
            @RequestAttribute("loginId") String loginId) {

        Long memberId = memberService.findIdByLoginId(loginId); // 로그인한 사용자 ID 조회

        List<BookDTO> likedBooks = bookService.findLikedBooks(memberId);

        return ResponseEntity.ok(likedBooks);
    }

}

