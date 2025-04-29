package com.project.poblog.global.auth.authenticationfilter;

import com.project.poblog.domain.user.service.UserGetService;
import com.project.poblog.global.auth.authentication.UsernamePasswordAuthentication;
import com.project.poblog.global.auth.authenticationprovider.JwtAuthenticationProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * JWT 토큰을 검증하고, SecurityContext에 인증 정보를 설정하는 필터
 * (매 요청마다 1번만 실행됨)
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final UserGetService userGetService;

    /**
     * 요청(Request)마다 실행되는 메서드
     * - Authorization 헤더에서 JWT 추출
     * - 토큰 검증
     * - 토큰에서 사용자 정보 추출
     * - SecurityContext에 Authentication 저장
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String token = request.getHeader("Authorization"); // Authorization 헤더에서 토큰 읽기

        String username = null;
        if (token != null && token.startsWith("Bearer ")) {
            String jwtToken = token.substring(7); // "Bearer " 접두어 제거
            jwtAuthenticationProvider.validateJwtAccessToken(jwtToken); // JWT 유효성 검사
            username = jwtAuthenticationProvider.getUserEmailFromToken(jwtToken); // JWT에서 이메일(또는 ID) 추출
        }

        // SecurityContext에 인증 객체가 비어있으면, 사용자 인증 정보 설정
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            SecurityContextHolder.getContext().setAuthentication(getUserAuth(username));
        }

        // 다음 필터로 요청 넘기기
        filterChain.doFilter(request, response);
    }

    /**
     * 사용자 이메일(username)로 Authentication 객체를 생성
     *
     * @param username 사용자 이메일
     * @return 생성된 Authentication 객체
     */
    private Authentication getUserAuth(String username) {
        var userInfo = userGetService.getUserByEmail(username); // DB에서 사용자 조회
        List<GrantedAuthority> authorities = List.of(() -> userInfo.getRole().getLabel()); // 권한(Role) 설정
        Authentication auth = new UsernamePasswordAuthentication(userInfo.getEmail(), userInfo.getPassword(), authorities);
        return auth;
    }

    /**
     * 필터를 적용하지 않을 URI를 정의
     *
     * @param request 현재 요청
     * @return true면 필터 적용 안함
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getRequestURI().equals("/user/login") || request.getRequestURI().equals("/user/join");
    }
}
