package com.example.demo.security;

import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    // 필터, 쿠키를 확인하는 과정으로 로그인 관련 api를 제외하고 다 통용되게 함
    // 만약 쿠키가 존재하지 않거나 유효하지 않다면 특수 메시지를 생성
    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        String uri = request.getRequestURI();

        if (uri.startsWith("/api/member/")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2) 쿠키에서 accessToken 찾기
        String token = extractTokenFromCookie(request);
//        System.out.println(">>>> 필터에 들어옴 토큰 값 : "+token);

        // 3) JWT 검증
        if (token != null && jwtUtil.validateToken(token)) {

            Claims claims = jwtUtil.getClaims(token);
            String loginId = claims.getSubject();

            request.setAttribute("loginId", loginId);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(loginId, null, List.of());

            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
            return;
        }

        filterChain.doFilter(request, response);
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

