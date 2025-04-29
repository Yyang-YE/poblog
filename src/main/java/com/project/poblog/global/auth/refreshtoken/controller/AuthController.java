package com.project.poblog.global.auth.refreshtoken.controller;

import com.project.poblog.global.auth.refreshtoken.dto.RefreshTokenRes;
import com.project.poblog.global.auth.refreshtoken.service.RefreshTokenService;
import com.project.poblog.global.response.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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


    @Operation(summary = "리프래시 토큰", description = "RTR 방식 사용: 어세스 토큰 리프래시 토큰 재생성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "정상 처리 되었습니다."),
            @ApiResponse(responseCode = "400", description = "입력이 잘못되었습니다.",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
    })
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