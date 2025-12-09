package com.example.demo.controller;

import com.example.demo.domain.Comment;
import com.example.demo.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class CommentController {

    private final CommentService commentService;

    // 댓글 등록
    @PostMapping("/api/books/{bookId}/comments")
    public ResponseEntity<Comment> addComment(
            @PathVariable Long bookId,
            @RequestBody Comment comment) {

        Comment saved = commentService.addComment(bookId, comment);
        return ResponseEntity.status(201).body(saved); // 201 Created
    }

    // 댓글 목록 조회
    @GetMapping("/api/books/{bookId}/comments")
    public ResponseEntity<List<Comment>> getComments(
            @PathVariable Long bookId) {

        return ResponseEntity.ok(commentService.getComments(bookId));
    }

    // 댓글 수정
    @PutMapping("/api/comments/{commentId}")
    public ResponseEntity<Comment> updateComment(
            @PathVariable Long commentId,
            @RequestBody Comment newData) {

        Comment updated = commentService.updateComment(commentId, newData);
        return ResponseEntity.ok(updated);
    }

    // 댓글 삭제
    @DeleteMapping("/api/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long commentId) {

        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build(); // 204
    }
}