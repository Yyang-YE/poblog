package com.project.poblog.global.auth.refreshtoken.service;

import com.project.poblog.global.auth.refreshtoken.dto.RefreshTokenDto;

public interface RefreshTokenService {
    RefreshTokenDto refreshToken(final String refreshToken);
}
