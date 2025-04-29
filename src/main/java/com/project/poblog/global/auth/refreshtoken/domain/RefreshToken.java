package com.project.poblog.global.auth.refreshtoken.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {
    protected static final Map<String, String> refreshTokenMap = new HashMap<>();

    public static String getRefreshToken(String token) {
        return Optional.ofNullable(refreshTokenMap.get(token)).orElseThrow();
    }

    public static void putRefreshToken(String token, String email) {
        refreshTokenMap.put(token, email);
    }

    public static void removeRefreshToken(String token) {
        refreshTokenMap.remove(token);
    }

    public static void removeUserRefreshToken(String token) {
        for(Map.Entry<String, String> entry : refreshTokenMap.entrySet()) {
            if(entry.getKey().equals(token)) {
                removeRefreshToken(entry.getKey());
            }
        }
    }
}
