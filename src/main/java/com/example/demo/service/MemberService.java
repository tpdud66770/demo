package com.example.demo.service;

import com.example.demo.domain.Member;
import com.example.demo.dto.MessageDTO;
import org.springframework.boot.web.server.Cookie;

public interface MemberService {

    // 회원가입 시 멤버 저장
    void joinMember(Member member);

    // 로그인 시 멤버 조회 후 토큰 발행
    String loginMember(Member member);

    MessageDTO logoutMember();
}
