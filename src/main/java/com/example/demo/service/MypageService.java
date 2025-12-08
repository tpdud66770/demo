package com.example.demo.service;

import com.example.demo.dto.BookDTO;
import org.springframework.stereotype.Service;

import java.util.List;


public interface MypageService {

    //1. 내가 등록한 도서 불러오기
    List<BookDTO> findRegisteredBook(String loginId);

}
