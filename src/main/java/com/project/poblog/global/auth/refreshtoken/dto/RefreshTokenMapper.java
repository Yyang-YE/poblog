package com.project.poblog.global.auth.refreshtoken.dto;

import org.mapstruct.Mapper;


public class RefreshTokenMapper {
    public static RefreshTokenRes toRefreshToken(String accessToken, String refreshToken){
        return new RefreshTokenRes(accessToken, refreshToken);
    }
}
