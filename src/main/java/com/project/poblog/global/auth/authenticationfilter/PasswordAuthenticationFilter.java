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
 * 비밀번호 검증이 필요한 요청을 처리하는 필터
 * - 요청 Body에서 password를 추출해 추가 인증 수행
 * - 인증 성공 시 다음 필터로 요청 전달
 */
@Component
@RequiredArgsConstructor
public class PasswordAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;

    /**
     * 실제 필터 로직 수행
     * - 요청 Body를 읽어 password 추출
     * - 현재 인증된 사용자의 이메일(email)을 SecurityContext에서 가져옴
     * - AuthenticationManager를 통해 email + password로 추가 인증 수행
     * - Body를 복구한 요청 객체를 다음 필터로 전달
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 요청 Body 추출
        byte[] bodyBytes = JsonRequestBodyUtil.extractRequestBody(request);

        // JSON 문자열을 Map으로 파싱
        Map<String, String> json = JsonRequestBodyUtil.parseToMap(bodyBytes);

        // password가 없으면 400 Bad Request 반환
        if (json == null || !json.containsKey("password")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // 현재 인증된 사용자 이메일 가져오기
        String password = json.get("password");
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();

        // email + password로 인증 시도
        Authentication auth = new UsernamePasswordAuthentication(email, password);
        authenticationManager.authenticate(auth);

        // 읽은 Body를 복구하여 다음 필터로 전달
        CachedBodyHttpServletRequest rebuildRequest = new CachedBodyHttpServletRequest(request, bodyBytes);
        filterChain.doFilter(rebuildRequest, response);
    }

    /**
     * 비밀번호 검증이 필요한 요청에만 필터 적용
     * - "/update/password" 또는 "/delete" URI를 포함하지 않으면 필터를 타지 않음
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getRequestURI().contains("/update/password") && !request.getRequestURI().contains("/delete");
    }
}
