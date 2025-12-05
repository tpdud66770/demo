package com.example.demo.filter;

import com.example.demo.component.JwtUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class JwtFilter implements Filter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    // 필터, 쿠키를 확인하는 과정으로 로그인 관련 api를 제외하고 다 통용되게 함
    // 만약 쿠키가 존재하지 않거나 유효하지 않다면 특수 메시지를 생성
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        System.out.println(">>> JwtFilter 실행됨"); // 로그

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();

        if (uri.startsWith("/api/member/")) {
            System.out.println(">>> 패싱하기"); // 로그
            chain.doFilter(request, response);
            return;
        }

        // 2) 쿠키에서 accessToken 찾기
        String token = extractTokenFromCookie(req);

        // 쿠키 없음 -> 여기서 바로 특수 응답 보내기
        if (token == null) {
            res.setCharacterEncoding("UTF-8");
            res.setContentType("application/json");
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401

            String json = """
        {
            "status": "fail",
            "message": "로그인이 필요합니다. accessToken 쿠키가 없습니다."
        }
        """;

            res.getWriter().write(json);
            return;
        }

        System.out.println(">>> 쿠키에서 받은 토큰: " + token);

        // 3) JWT 검증
        if (jwtUtil.validateToken(token)) {
            System.out.println(">>> 토큰 유효함");
            Claims claims = jwtUtil.getClaims(token);
            request.setAttribute("loginId", claims.getSubject());
        } else {
            System.out.println(">>> 토큰 유효하지 않음");

            res.setCharacterEncoding("UTF-8");
            res.setContentType("application/json");
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401

            String json = """
        {
            "status": "fail",
            "message": "토큰이 유효하지 않습니다."
        }
        """;

            res.getWriter().write(json);
            return;
        }

        System.out.println(">>> 필터 마지막 단계");

        chain.doFilter(request, response);
    }

    private String extractTokenFromCookie(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();

        if (cookies == null) return null;

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("accessToken")) {
                return cookie.getValue(); // 쿠키에는 Bearer 없음 → 바로 반환
            }
        }
        return null;
    }
}

