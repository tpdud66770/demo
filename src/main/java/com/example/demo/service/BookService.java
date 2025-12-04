package com.example.demo.service;


import com.example.demo.domain.Book;
import com.example.demo.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    // 책 전체 조회
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    // 책 등록
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    // 상세 조회
    public Book detail(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("책을 찾지 못했습니다 ."));
    }

    // 책 업데이트
    public Book update(Long id , Book newData) {
        Book book = detail(id);

        book.setTitle(newData.getTitle());
        book.setContent(newData.getContent());
        book.setImageUrl(newData.getImageUrl());

        return bookRepository.save(book);
    }

    // 책 삭제
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }
}
