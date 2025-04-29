package com.project.poblog.global.auth.authenticationfilter;

import com.project.poblog.global.auth.authentication.UsernamePasswordAuthentication;
import com.project.poblog.global.auth.util.JsonRequestBodyUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

/**
 * 로그인 요청을 처리하는 필터
 * - 로그인 요청의 Body(JSON)에서 이메일과 비밀번호를 추출
 * - AuthenticationManager를 통해 인증 시도
 * - 성공 시 SecurityContext에 인증 객체 저장
 */
@Component
@RequiredArgsConstructor
public class LoginAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;

    /**
     * 실제 필터 로직 수행
     * - Request Body를 읽어 email, password 추출
     * - AuthenticationManager로 인증 시도
     * - 인증 성공 시 SecurityContextHolder에 인증 정보 저장
     * - Body를 복구한 요청 객체를 다음 필터로 전달
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 요청 바디(byte[]) 추출
        byte[] bodyBytes = JsonRequestBodyUtil.extractRequestBody(request);

        // JSON 문자열을 Map으로 파싱
        Map<String, String> json = JsonRequestBodyUtil.parseToMap(bodyBytes);

        // email 또는 password가 없으면 400 Bad Request 응답
        if (json == null || !json.containsKey("email") || !json.containsKey("password")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // 요청 본문에서 email, password 추출
        String username = json.get("email");
        String password = json.get("password");

        // Authentication 객체 생성 후 인증 시도
        Authentication auth = new UsernamePasswordAuthentication(username, password);
        authenticationManager.authenticate(auth);

        // 인증 성공 시 SecurityContext에 저장
        SecurityContextHolder.getContext().setAuthentication(auth);

        // 읽은 바디를 복구해서 새로운 Request로 감싼 뒤, 다음 필터로 넘김
        CachedBodyHttpServletRequest rebuildRequest = new CachedBodyHttpServletRequest(request, bodyBytes);
        filterChain.doFilter(rebuildRequest, response);
    }

    /**
     * 로그인 API 경로가 아니면 이 필터를 타지 않도록 설정
     *
     * @param request 현재 요청
     * @return true이면 필터를 타지 않음
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getRequestURI().equals("/user/login");
    }
}
