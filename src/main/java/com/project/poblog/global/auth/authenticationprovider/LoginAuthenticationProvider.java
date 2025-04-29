package com.project.poblog.global.auth.authenticationprovider;

import com.project.poblog.domain.user.service.UserGetService;
import com.project.poblog.global.auth.authentication.UsernamePasswordAuthentication;
import com.project.poblog.global.exception.GlobalException;
import com.project.poblog.global.response.ResultCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * 사용자 로그인 인증을 처리하는 AuthenticationProvider
 */
@Component
@RequiredArgsConstructor
public class LoginAuthenticationProvider implements AuthenticationProvider {

    private final UserGetService userGetService;

    /**
     * 인증 요청 처리 메서드
     * - 이메일과 비밀번호를 검증하여 유효한 사용자이면 인증 객체를 반환
     * - 유효하지 않으면 예외(GlobalException) 발생
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String email = authentication.getName(); // 요청으로부터 이메일 추출
        String password = String.valueOf(authentication.getCredentials()); // 요청으로부터 비밀번호 추출

        // 이메일과 비밀번호 검증
        if (userGetService.isValidUser(email, password)) {
            // 인증 성공 시 UsernamePasswordAuthentication 객체 반환 (credentials는 null 처리)
            return new UsernamePasswordAuthentication(
                    email,
                    null
            );
        }

        // 인증 실패 시 예외 발생
        throw new GlobalException(ResultCode.LOG_IN_REQUIRED);
    }

    /**
     * 현재 Provider가 지원하는 Authentication 타입인지 확인
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthentication.class.isAssignableFrom(authentication);
    }
}
