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

    @PostMapping
    public ResponseEntity<MessageDTO> join(@RequestBody Member member){

        memberService.joinMember(member);

        MessageDTO response = new MessageDTO();
        response.setStatus("success");
        response.setMessage("회원가입 성공");

        return ResponseEntity.ok(response);
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

        System.out.println("login-cookie token : "+cookie);
        MessageDTO response = new MessageDTO();
        response.setStatus("success");
        response.setMessage("로그인 성공");

        return ResponseEntity.ok()
                .header("Set-Cookie", cookie.toString())       // 쿠키 추가 (중요)
                .body(response);
    }

    @GetMapping("/logout")
    public ResponseEntity<MessageDTO> logout(){

        ResponseCookie cookie = ResponseCookie.from("accessToken", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)  // 쿠키 즉시 만료
                .build();

        System.out.println("logout-cookie token : "+cookie);
        MessageDTO response = new MessageDTO();
        response.setStatus("success");
        response.setMessage("로그아웃 성공");

        return ResponseEntity.ok(response);
    }

    //아이디 중복 확인 체크
    @PostMapping("/check/{checkId}")
    public ResponseEntity<?> checkLoginId(@PathVariable String checkId){

        System.out.println("checkId : "+checkId);

        boolean checking = memberService.findLoginIdbyCheckId(checkId);

        System.out.println("checking : "+checking);

        if(checking){
            MessageDTO response = new MessageDTO();
            response.setStatus("중복");
            response.setMessage("중복된 아이디입니다.");
            return ResponseEntity.ok(response);

        }else{

            MessageDTO response = new MessageDTO();
            response.setStatus("사용가능");
            response.setMessage("사용가능 한 아이디입니다.");
            return ResponseEntity.ok(response);

        }

    }
}
