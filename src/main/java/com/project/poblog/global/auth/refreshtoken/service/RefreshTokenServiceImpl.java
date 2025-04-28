package com.project.poblog.global.auth.refreshtoken.service;

import com.project.poblog.global.auth.authenticationprovider.JwtAuthenticationProvider;
import com.project.poblog.global.auth.refreshtoken.dto.RefreshTokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    @Override
    public RefreshTokenDto refreshToken(String refreshToken) {
        return null;
    }

    private void checkRefreshToken(final String refreshToken) {
        jwtAuthenticationProvider.validateJwtRefreshToken(refreshToken);
    }

}
