package com.example.demo.service;

import com.example.demo.domain.Member;
import com.example.demo.dto.MessageDTO;

public interface MemberService {

    // 회원가입 시 멤버 저장
    MessageDTO save(Member member);

    // 로그인 시 멤버 조회
    MessageDTO findMember(Member member);
}
