package com.example.demo.configuration;

import com.example.demo.component.JwtUtil;
import com.example.demo.filter.JwtFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    /*토큰 검사가 api의 응답을 받기 전,
    필터에서 이루어지므로
    스프링 부트에서 제공하는,
    필터에 등록함
    * */
    @Bean
    public FilterRegistrationBean<JwtFilter> jwtFilter(JwtUtil jwtUtil) {
        FilterRegistrationBean<JwtFilter> filter = new FilterRegistrationBean<>();

        filter.setFilter(new JwtFilter(jwtUtil));
        filter.addUrlPatterns("/api/*"); // 여기 적으면 /api/ 로 시작하는 모든 API 검사됨

        return filter;
    }
}

