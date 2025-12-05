package com.example.demo.service;

import com.example.demo.component.JwtUtil;
import com.example.demo.domain.Member;
import com.example.demo.dto.MessageDTO;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    /*
    * 회원가입 기능
    * 회원 정보를 저장한다
    * */
    @Override
    public void joinMember(Member member){
        memberRepository.save(member);
    }

    /*
    * 로그인 기능
    * 아이디와 비번이 담긴 멤버를 받는다 (네임은 받지 않는다)
    * 멤버에서 아이디를 읽고 데이터베이스에 아이디가 있는지 확인한다
    * 맞는 아이디가 있다면 해당 멤버를 반환하도록 한다
    * 이후 비번을 비교한다
    * 비번이 맞다면 성공 객체를 반환하면서 토큰을 발행한다
    * */
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
    public MessageDTO logoutMember(){
        MessageDTO message = new MessageDTO();
        message.setStatus("success");
        message.setMessage("로그아웃 성공");
        return message;
    }

}
