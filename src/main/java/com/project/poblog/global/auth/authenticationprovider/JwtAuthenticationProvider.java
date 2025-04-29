package com.project.poblog.global.auth.authenticationprovider;

import com.project.poblog.global.exception.GlobalException;
import com.project.poblog.global.response.ResultCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

/**
 * JWT 토큰 생성 및 검증을 담당하는 Provider 클래스
 */
@Component
public class JwtAuthenticationProvider {

    // JWT Access 토큰 만료 시간 (30분)
    private static final Long JWT_EXPIRATION_TIME = (Long) 1000L * 30L * 30L;
    // JWT Refresh 토큰 만료 시간 (1일)
    private static final Long JWT_REFRESH_TIME = (Long) 1000L * 60L * 60L * 24L;

    @Value("${jwt.secret}") // application.yml에 정의된 시크릿 키 주입
    private String secret;

    private SecretKey key;

    /**
     * Bean 초기화 시점에 SecretKey 객체 생성
     */
    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * 토큰에서 사용자 이메일(Subject)을 추출
     */
    public String getUserEmailFromToken(final String token) {
        return getClaim(token, Claims::getSubject);
    }

    /**
     * 토큰에서 원하는 Claim 값을 추출
     * @param claimsResolver Claims에서 필요한 정보를 뽑는 함수형 인터페이스
     */
    public <T> T getClaim(final String token, final Function<Claims, T> claimsResolver) {
        validateJwtAccessToken(token);

        final Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claimsResolver.apply(claims);
    }

    /**
     * Access 토큰 유효성 검증
     * - 만료, 변조 여부 체크
     */
    public void validateJwtAccessToken(final String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
        } catch (SecurityException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            throw new GlobalException(ResultCode.INVALID_TOKEN);
        } catch (ExpiredJwtException e) {
            throw new GlobalException(ResultCode.ACCESS_TOKEN_EXPIRED);
        }
    }

    /**
     * Refresh 토큰 유효성 검증
     * - 만료, 변조 여부 체크
     */
    public void validateJwtRefreshToken(final String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
        } catch (SecurityException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            throw new GlobalException(ResultCode.INVALID_TOKEN);
        } catch (ExpiredJwtException e) {
            throw new GlobalException(ResultCode.REFRESH_TOKEN_EXPIRED);
        }
    }

    /**
     * Access 토큰 생성
     * - 사용자 email과 추가 claims 정보 포함
     */
    public String doGenerateAccessToken(final String email, final Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    /**
     * Refresh 토큰 생성
     * - 사용자 email만 포함 (추가 claims 없음)
     */
    public String doGenerateRefreshToken(final String email) {
        return Jwts.builder()
                .setSubject(email)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_REFRESH_TIME)) // 1일
                .signWith(key)
                .compact();
    }
}
