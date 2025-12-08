package com.example.demo.service;

import com.example.demo.domain.Book;
import com.example.demo.dto.BookDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.LikeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
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
                    dto.setImgUrl(book.getImgUrl());
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
                .orElseThrow(() -> new ResourceNotFoundException("책을 찾지 못했습니다. bookId= " + bookId));

        book.setViewCnt(book.getViewCnt() + 1);
        book.setUpdateTime(LocalDate.now()); // 조회시 업데이트 시간 갱신 여부는 선택
        return book;
    }

    @Override
    @Transactional
    public Book update(Long bookId, Book newData) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("책을 찾지 못했습니다. bookId= " + bookId));

        book.setTitle(newData.getTitle());
        book.setContent(newData.getContent());
        book.setAuthor(newData.getAuthor());
        book.setUpdateTime(LocalDate.now());
        book.setImgUrl(newData.getImgUrl());// update_time 저장

        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public void delete(Long bookId) {
        bookRepository.deleteById(bookId);
    }

    @Override
    @Transactional
    public Book updateBookCoverUrl(Long bookId, String imgUrl) {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("책을 찾지 못했습니다. bookId = " + bookId));

        book.setImgUrl(imgUrl);
        book.setUpdateTime(LocalDate.now());

        return bookRepository.save(book);
    }

    @Override
    public boolean likeToggle(Long book_id,Long member_id) {

        boolean exists = likeRepository.existsByMember_IdAndBook_BookId(book_id, member_id);

        log.info("=================");
        log.info(exists);
        log.info("=================");

        if (exists) {
            likeRepository.likeToggle(book_id, member_id);
            return likeRepository.findLikeYn(book_id, member_id);
        } else {
            likeRepository.insertLike(book_id, member_id);
            return likeRepository.findLikeYn(book_id, member_id);
        }
    }

    @Override
    public List<BookDTO> findLikedBooks (Long member_id){

            // 1) 좋아요 누른 Book 목록 조회
        List<Book> books = likeRepository.findLikedBooksByMemberId(member_id);

            // 2) Book → BookDTO 변환
        return books.stream()
                .map(book -> {
                    BookDTO dto = new BookDTO();
                    dto.setBookId(book.getBookId());
                    dto.setTitle(book.getTitle());
                    dto.setAuthor(book.getAuthor());
                    dto.setViewCnt(book.getViewCnt());
                    dto.setImgUrl(book.getImgUrl());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}


