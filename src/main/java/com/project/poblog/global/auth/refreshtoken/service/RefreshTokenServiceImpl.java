package com.project.poblog.global.auth.refreshtoken.service;

import com.project.poblog.global.auth.authenticationprovider.JwtAuthenticationProvider;
import com.project.poblog.global.auth.refreshtoken.domain.RefreshToken;
import com.project.poblog.global.auth.refreshtoken.dto.RefreshTokenMapper;
import com.project.poblog.global.auth.refreshtoken.dto.RefreshTokenRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;


@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    @Override
    public RefreshTokenRes refreshToken(String refreshToken) {
        checkRefreshToken(refreshToken);
        var email = RefreshToken.getRefreshToken(refreshToken);

        String newAccessToken = jwtAuthenticationProvider.doGenerateAccessToken(email, new HashMap<>());
        String newRefreshToken = RefreshToken.getRefreshToken(refreshToken);
        RefreshToken.putRefreshToken(refreshToken, newRefreshToken);

        return RefreshTokenMapper.toRefreshToken(newAccessToken, newRefreshToken);
    }

    private void checkRefreshToken(final String refreshToken) {
        jwtAuthenticationProvider.validateJwtRefreshToken(refreshToken);
    }

}
