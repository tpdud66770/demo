package com.example.demo.configuration;

import com.example.demo.security.JwtFilter;
import com.example.demo.security.JwtAuthenticationEntryPoint;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))   // ⭐ CORS 설정 추가
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                .securityMatcher("/api/**")

                .headers(headers -> headers.frameOptions(frame -> frame.disable()))

                .exceptionHandling(ex -> ex.authenticationEntryPoint(authenticationEntryPoint))

                .authorizeHttpRequests(auth -> auth
                        // preflight
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // 인증 없이 접근 가능
                        .requestMatchers("/api/member/**").permitAll()
                        .requestMatchers("/api/main/**").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()

                        // 조회는 비로그인 허용
                        .requestMatchers(HttpMethod.GET, "/api/books/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/books/*/comments").permitAll()

                        // 그 외 (좋아요, 작성, 수정, 삭제)는 로그인 필수
                        .requestMatchers("/api/books/**").authenticated()
                        .requestMatchers("/api/comments/**").authenticated()
                        .requestMatchers("/api/mypage/**").authenticated()

                        .anyRequest().authenticated()
                )


                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // ⭐⭐ CORS 설정 Bean — 프론트에서 오는 요청을 모두 허용
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.addAllowedOriginPattern("*");     // http://localhost:5173 포함 전체 허용
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(
                        "/", "/favicon.ico", "/static/**", "/assets/**",
                        "/index.html", "/h2-console/**",
                        "/api-docs/**", "/swagger-ui/**"
                );
    }
}

