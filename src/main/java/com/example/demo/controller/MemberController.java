package com.example.demo.controller;

import com.example.demo.domain.Member;
import com.example.demo.dto.MessageDTO;
import com.example.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
@CrossOrigin("*")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity<MessageDTO> join(@RequestBody Member member){

        memberService.joinMember(member);

        MessageDTO response = new MessageDTO();
        response.setStatus("success");
        response.setMessage("회원가입 성공");

        return ResponseEntity.ok()
                .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<MessageDTO> login(@RequestBody Member member){
        String token =  memberService.loginMember(member);

        ResponseCookie cookie = ResponseCookie.from("accessToken", token)
                .httpOnly(true)
                .secure(false)     // HTTPS 환경이면 true 권장
                .path("/")
                .maxAge(60 * 60 * 24)
                .build();

        MessageDTO response = new MessageDTO();
        response.setStatus("success");
        response.setMessage("로그인 성공");

        return ResponseEntity.ok()
                .header("Set-Cookie", cookie.toString())       // 쿠키 추가 (중요)
                .body(response);
    }

    @GetMapping("/logout")
    public ResponseEntity<MessageDTO> logout(){
        MessageDTO response =  memberService.logoutMember();

        ResponseCookie cookie = ResponseCookie.from("accessToken", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)  // 쿠키 즉시 만료
                .build();

        return ResponseEntity.ok()
                .body(response);
    }
}
