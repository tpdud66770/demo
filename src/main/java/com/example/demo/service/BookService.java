package com.example.demo.service;

import com.example.demo.domain.Book;
import com.example.demo.dto.BookDTO;

import java.util.List;

public interface BookService {

    List<BookDTO> findAll();

    Book save(Book book);

    Book detail(Long id);

    Book update(Long id, Book newData);

    Book toggleLiked(Long id);

    void delete(Long id);
}
