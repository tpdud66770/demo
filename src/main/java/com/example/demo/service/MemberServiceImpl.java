package com.example.demo.service;

import com.example.demo.security.JwtUtil;
import com.example.demo.domain.Member;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    @Override
    public void joinMember(Member member){
        try {
            memberRepository.save(member);
        } catch (Exception e) {
            throw new IllegalArgumentException("회원가입이 불가합니다");
        }
    }

    @Override
    public String loginMember(Member member){
        Optional<Member> matchingMember = memberRepository.findByLoginId(member.getLoginId());

        Member findingMember = matchingMember.orElseThrow(
                () -> new IllegalArgumentException("회원이 존재하지 않습니다.")
        );

        if (!member.getPass().equals(findingMember.getPass())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return jwtUtil.generateToken(findingMember.getLoginId());
    }

    @Override
    public Long findIdByLoginId(String loginId) {
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));
        return member.getId();
    }

    @Override
    public Member findByLoginId(String loginId){
        return memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new RuntimeException("유저 아이디를 찾을 수 없습니다."));
    }

    @Override
    public boolean findLoginIdbyCheckId(String checkId) {
        return memberRepository.checkDuplication(checkId);
    }

    @Override
    public Member findById(Long id) {
        return memberRepository.findById(id)
                .orElse(null);
    }



}
