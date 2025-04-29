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

@Component
@RequiredArgsConstructor
public class LoginAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        byte[] bodyBytes = JsonRequestBodyUtil.extractRequestBody(request);

        // Json 문자열을 Map으로 파싱
        Map<String, String> json = JsonRequestBodyUtil.parseToMap(bodyBytes);

        // username 또는 password가 없으면 400(Bad Request) 응답
        if (json == null || !json.containsKey("email") || !json.containsKey("password")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String username = json.get("email");
        String password = json.get("password");

        Authentication auth = new UsernamePasswordAuthentication(username, password);
        authenticationManager.authenticate(auth);
        SecurityContextHolder.getContext().setAuthentication(auth);

        // 한번 읽은 입력 스트림을 다시 사용할 수 있도록 요청 래핑
        CachedBodyHttpServletRequest rebuildRequest = new CachedBodyHttpServletRequest(request, bodyBytes);

        // 다음 필터로 요청 전달
        filterChain.doFilter(rebuildRequest, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getRequestURI().equals("/user/login");
    }
}
