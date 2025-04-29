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

@Component
public class JwtAuthenticationProvider {
    // JWT Access 토큰 만료 시간 30분
    private static final Long JWT_EXPIRATION_TIME = (Long) 1000L * 30L * 30L;
    private static final Long JWT_REFRESH_TIME = (Long) 1000L * 60L * 60L * 24L;

    @Value("${jwt.secret}")
    private String secret;

    private SecretKey key;

    @PostConstruct
    public void init(){
        key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String getUserEmailFromToken(final String token) {
        return getClaim(token, Claims::getSubject);
    }

    public Date getIssuedAtFromToken(final String token) {
        return getClaim(token, Claims::getExpiration);
    }

    public <T> T getClaim(final String token, final Function<Claims, T> claimsResolver) {
        validateJwtAccessToken(token);

        final Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claimsResolver.apply(claims);
    }


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


    public String doGenerateAccessToken(final String email, final Map<String, Object> claims) {

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ JWT_EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    public String doGenerateRefreshToken(final String email) {
        return Jwts.builder()
                .setSubject(email)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+JWT_REFRESH_TIME)) //하루
                .signWith(key)
                .compact();
    }

}
