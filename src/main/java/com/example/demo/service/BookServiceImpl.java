package com.example.demo.service;

import com.example.demo.domain.Book;
import com.example.demo.dto.BookDTO;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.LikeRepository;
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
    private final LikeRepository likeRepository;

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
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Book save(Book book) {
        book.setRegTime(LocalDate.now());      // reg_time
        book.setUpdateTime(LocalDate.now());   // update_time
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public Book detail(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("책을 찾지 못했습니다."));

        book.setViewCnt(book.getViewCnt() + 1);
        book.setUpdateTime(LocalDate.now()); // 조회시 업데이트 시간 갱신 여부는 선택
        return book;
    }

    @Override
    public Book update(Long bookId, Book newData) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("책을 찾지 못했습니다."));

        book.setTitle(newData.getTitle());
        book.setContent(newData.getContent());
        book.setAuthor(newData.getAuthor());
        book.setUpdateTime(LocalDate.now());   // update_time 저장

        return bookRepository.save(book);
    }

    @Override
    public void delete(Long bookId) {
        bookRepository.deleteById(bookId);
    }

    @Override
    public void likeToggle(Long member_id,Long book_id){

        boolean exists = likeRepository.existsByMember_IdAndBook_BookId(member_id, book_id);

        if (exists) {
            likeRepository.likeToggle(member_id, book_id);
        } else {
            likeRepository.insertLike(member_id, book_id);
        }
    }
}


