package com.example.demo.service;

import com.example.demo.domain.Book;
import com.example.demo.domain.Comment;
import com.example.demo.domain.Member;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.UnauthorizedActionException;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final HttpServletRequest request;
    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    // -------------------------
    // 1. 댓글 등록
    // -------------------------
    @Override
    @Transactional
    public Comment addComment(Long bookId, Comment comment) {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("책을 찾을 수 없습니다. bookId=" + bookId));

        // JWT 필터에서 넣어둔 loginId 꺼내기
        String loginId = (String) request.getAttribute("loginId");

        if (loginId == null) {
            throw new RuntimeException("로그인 정보가 필요합니다.");
        }

        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new ResourceNotFoundException("회원 정보를 찾을 수 없습니다."));

        // author 자동
        comment.setAuthor(member.getName());

        comment.setBook(book);
        comment.setMember(member);
        comment.setRegTime(LocalDate.now());
        comment.setUpdateTime(LocalDate.now());

        return commentRepository.save(comment);
    }


    // 댓글 목록 조회
    @Override
    public List<Comment> getComments(Long bookId) {
        return commentRepository.findByBook_BookId(bookId);
    }


    // 댓글 수정
    @Override
    @Transactional
    public Comment updateComment(Long commentId, Comment newData) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("댓글을 찾을 수 없습니다. commentId=" + commentId));

        // 로그인 사용자 확인
        String loginId = (String) request.getAttribute("loginId");
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new ResourceNotFoundException("회원 정보를 찾을 수 없습니다."));

        // 작성자 본인인지 체크
        if (!comment.getMember().getId().equals(member.getId())) {
            throw new UnauthorizedActionException("본인이 작성한 댓글만 수정할 수 있습니다.");
        }

        comment.setContent(newData.getContent());
        comment.setUpdateTime(LocalDate.now());

        return commentRepository.save(comment);
    }


    // 댓글 삭제 — 작성자 본인만
    @Override
    @Transactional
    public void deleteComment(Long commentId) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("댓글을 찾을 수 없습니다. commentId=" + commentId));

        String loginId = (String) request.getAttribute("loginId");
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new ResourceNotFoundException("회원 정보를 찾을 수 없습니다."));

        // 작성자 본인인지 체크
        if (!comment.getMember().getId().equals(member.getId())) {
            throw new UnauthorizedActionException("본인이 작성한 댓글만 삭제할 수 있습니다.");
        }

        commentRepository.delete(comment);
    }
}

