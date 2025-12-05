package com.example.demo.service;

import com.example.demo.domain.Member;
import com.example.demo.dto.BookDTO;
import com.example.demo.dto.MessageDTO;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    // 회원가입 시 멤버 저장
    @Override
    public MessageDTO save(Member member){

        memberRepository.save(member);
        MessageDTO message = new MessageDTO();
        message.setStatus("success");
        message.setMessage("회원가입 성공");
        return message;
    }

    // 로그인 시 멤버 조회
    /*
    * 아이디와 비번이 담긴 멤버를 받는다 (아이디와 네임은 받지 않는다)
    * 멤버에서 아이디를 읽고 데이터베이스에 아이디가 있는지 확인한다
    * 맞는 아이디가 있다면 해당 멤버를 반환하도록 한다
    * 이후 비번을 비교한다 비번 비교한다
    * */
    @Override
    public MessageDTO findMember(Member member){
        Optional<Member> matchingMember = memberRepository.findByLoginId(member.getLoginId());

        Member findingMember = matchingMember.orElseThrow(
                () -> new IllegalArgumentException("회원이 존재하지 않습니다.")
        );
        if (!member.getPass().equals(findingMember.getPass())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        MessageDTO message = new MessageDTO();
        message.setStatus("success");
        message.setMessage("로그인 성공");
        return message;
    }

}
