package com.project.poblog.domain.user.service;

import com.project.poblog.domain.user.entity.User;
import com.project.poblog.domain.user.dto.UserMapper;
import com.project.poblog.domain.user.dto.request.LoginReq;
import com.project.poblog.domain.user.dto.request.JoinReq;
import com.project.poblog.domain.user.dto.response.LoginRes;
import com.project.poblog.domain.user.dto.response.JoinRes;
import com.project.poblog.domain.user.repository.UserRepository;
import com.project.poblog.global.auth.authenticationprovider.JwtAuthenticationProvider;
import com.project.poblog.global.auth.refreshtoken.domain.RefreshToken;
import com.project.poblog.global.exception.GlobalException;
import com.project.poblog.global.response.ResultCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final PasswordEncoder passwordEncoder;

    public JoinRes join(JoinReq registerUserRequest) {
        User user = userMapper.toEntity(registerUserRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User saveUser = userRepository.save(user);
        return userMapper.toRegisterResponse(saveUser);
    }

    public LoginRes login(LoginReq loginUserRequest) {

        User user = userRepository.findByEmail(loginUserRequest.getEmail()).orElseThrow(()->
                new GlobalException(ResultCode.NOT_FOUND_USER));

        // access 토큰 발급, refresh 토큰 제거
        String accessToken = jwtAuthenticationProvider.doGenerateAccessToken(String.valueOf(user.getId()),new HashMap<>());
        RefreshToken.removeUserRefreshToken(accessToken);

        // refresh 토큰 재 발급
        String refreshToken = jwtAuthenticationProvider.doGenerateRefreshToken(loginUserRequest.getEmail());
        RefreshToken.putRefreshToken(refreshToken,user.getId());

        LoginRes res = userMapper.toLoginUserResponse(user);
        res.setAccessToken(accessToken);
        res.setRefreshToken(refreshToken);

        return res;
    }
}
