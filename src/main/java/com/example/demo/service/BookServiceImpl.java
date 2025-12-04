package com.example.demo.service;

import com.example.demo.domain.Book;
import com.example.demo.dto.BookDTO;
import com.example.demo.repository.BookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public List<BookDTO> findAll() {

        return bookRepository.findAll()
                .stream()
                .map(book -> {
                    BookDTO dto = new BookDTO();
                    dto.setBookId(book.getBookId());
                    dto.setTitle(book.getTitle());
                    dto.setAuthor(book.getAuthor());
                    dto.setViewCnt(book.getViewCnt());
                    dto.setLiked(book.getLiked());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Book save(Book book) {
        book.setCreateAt(LocalDate.now());   // create_at
        book.setUpdateAt(LocalDate.now());   // update_at
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public Book detail(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("책을 찾지 못했습니다."));

        book.setViewCnt(book.getViewCnt() + 1);
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public Book toggleLiked(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("책을 찾지 못했습니다."));
        book.setLiked(!book.getLiked());
        return bookRepository.save(book);
    }

    @Override
    public Book update(Long bookId, Book newData) {
        Book book = detail(bookId);

        book.setTitle(newData.getTitle());
        book.setContent(newData.getContent());
        book.setAuthor(newData.getAuthor());
        book.setUpdateAt(LocalDate.now());   // 수정된 부분!!!!

        return bookRepository.save(book);
    }

    @Override
    public void delete(Long bookId) {
        bookRepository.deleteById(bookId);
    }
}

