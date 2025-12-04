package com.example.demo.controller;

import com.example.demo.domain.Member;
import com.example.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
@CrossOrigin("*")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    public void join(@RequestBody Member member){
        member.setId(1L);
        memberService.save(member);
    }

    @PostMapping("/login")
    public void login(@RequestBody Member member){
        memberService.findMember(member);
    }

    @PostMapping("/logout")
    public void logout(){

    }
}
