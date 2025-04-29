package com.project.poblog.global.auth.refreshtoken.dto;



public class RefreshTokenMapper {
    public static RefreshTokenRes toRefreshToken(String accessToken, String refreshToken){
        return new RefreshTokenRes(accessToken, refreshToken);
    }
}
