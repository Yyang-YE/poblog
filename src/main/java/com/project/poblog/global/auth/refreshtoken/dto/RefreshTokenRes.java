package com.project.poblog.global.auth.refreshtoken.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RefreshTokenRes {
    private String accessToken;
    private String refreshToken;
}
