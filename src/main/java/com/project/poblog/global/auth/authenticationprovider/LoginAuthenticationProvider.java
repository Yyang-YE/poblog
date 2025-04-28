package com.project.poblog.global.auth.authenticationprovider;

import com.project.poblog.domain.user.entity.Role;
import com.project.poblog.domain.user.entity.User;
import com.project.poblog.domain.user.service.UserGetService;
import com.project.poblog.domain.user.service.UserService;
import com.project.poblog.global.auth.authentication.UsernamePasswordAuthentication;
import com.project.poblog.global.exception.GlobalException;
import com.project.poblog.global.response.ResultCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LoginAuthenticationProvider implements AuthenticationProvider {

    private final UserGetService userGetService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String email = authentication.getName();
        String password = String.valueOf(authentication.getCredentials());

        if(userGetService.isValidUser(email, password)){
            return new UsernamePasswordAuthentication(
                    email,
                    null
            );
        }

        throw new GlobalException(ResultCode.LOG_IN_REQUIRED);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthentication.class.isAssignableFrom(authentication);
    }

}
