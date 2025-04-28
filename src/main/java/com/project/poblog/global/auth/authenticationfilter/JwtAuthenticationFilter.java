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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final UserGetService userGetService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String token = request.getHeader("Authorization");
        String username = null;
        if(token != null && token.startsWith("Bearer ")) {
            String jwtToken = token.substring(7);
            username = jwtAuthenticationProvider.getUserIdFromToken(jwtToken);
        }

        if(username != null && !username.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null) {
            SecurityContextHolder.getContext().setAuthentication(getUserAuth(Long.parseLong(username)));
        }

        filterChain.doFilter(request, response);
    }

    private Authentication getUserAuth(Long id) {
        var userInfo = userGetService.getUserById(id);
        return new UsernamePasswordAuthentication(userInfo.getEmail(), userInfo.getPassword());
    }
}
