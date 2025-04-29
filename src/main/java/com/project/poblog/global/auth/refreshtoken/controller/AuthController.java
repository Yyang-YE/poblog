package com.project.poblog.global.auth.refreshtoken.controller;

import com.project.poblog.global.auth.refreshtoken.dto.RefreshTokenRes;
import com.project.poblog.global.auth.refreshtoken.service.RefreshTokenService;
import com.project.poblog.global.response.CommonResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final RefreshTokenService refreshTokenService;

    @PostMapping("/refresh")
    public CommonResponse<RefreshTokenRes> refreshToken(HttpServletRequest request) {
        String refreshToken = resolveToken(request);
        RefreshTokenRes response = refreshTokenService.refreshToken(refreshToken);

        return CommonResponse.success(response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}