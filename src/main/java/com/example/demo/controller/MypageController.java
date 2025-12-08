package com.example.demo.controller;

import com.example.demo.domain.Member;
import com.example.demo.dto.MessageDTO;
import com.example.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
@CrossOrigin("*")
public class MypageController {

    private final MemberService memberService;


}

