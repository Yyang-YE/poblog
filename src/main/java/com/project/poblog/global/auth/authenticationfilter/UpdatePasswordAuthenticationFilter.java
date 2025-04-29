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
public class UpdatePasswordAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        byte[] bodyBytes = JsonRequestBodyUtil.extractRequestBody(request);

        Map<String, String> json = JsonRequestBodyUtil.parseToMap(bodyBytes);

        if (json == null || !json.containsKey("password")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String password = json.get("password");
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        Authentication auth = new UsernamePasswordAuthentication(email, password);
        authenticationManager.authenticate(auth);

        CachedBodyHttpServletRequest rebuildRequest = new CachedBodyHttpServletRequest(request, bodyBytes);

        filterChain.doFilter(rebuildRequest, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getRequestURI().contains("/update/password");
    }

}
