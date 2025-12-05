package com.example.demo.controller;

import com.example.demo.domain.Member;
import com.example.demo.dto.MessageDTO;
import com.example.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
@CrossOrigin("*")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    public MessageDTO join(@RequestBody Member member){
        return memberService.save(member);
    }

    @PostMapping("/login")
    public MessageDTO login(@RequestBody Member member){
        return memberService.findMember(member);
    }

    @PostMapping("/logout")
    public void logout(){

    }
}
