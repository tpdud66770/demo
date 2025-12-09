package com.example.demo.service;

import com.example.demo.domain.Member;

public interface MemberService {

    // 회원가입 시 멤버 저장
    void joinMember(Member member);

    // 로그인 시 멤버 조회 후 토큰 발행
    String loginMember(Member member);

    Long findIdByLoginId(String loginId);

    boolean findLoginIdbyCheckId(String checkId);

    Member findByLoginId(String loginId);

    Member findById(Long id);

}
