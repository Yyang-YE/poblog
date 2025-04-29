package com.project.poblog.global.auth.refreshtoken.service;

import com.project.poblog.global.auth.refreshtoken.dto.RefreshTokenRes;

public interface RefreshTokenService {
    RefreshTokenRes refreshToken(final String refreshToken);
}
