package com.project.poblog.domain.user.service;

import com.project.poblog.domain.user.dto.request.*;
import com.project.poblog.domain.user.dto.response.*;
import com.project.poblog.domain.user.entity.User;
import com.project.poblog.domain.user.dto.UserMapper;
import com.project.poblog.domain.user.repository.UserRepository;
import com.project.poblog.global.auth.authenticationprovider.JwtAuthenticationProvider;
import com.project.poblog.global.auth.refreshtoken.domain.RefreshToken;
import com.project.poblog.global.exception.GlobalException;
import com.project.poblog.global.response.ResultCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public JoinUserRes joinUser(JoinUserReq registerUserRequest) {
        User user = userMapper.toEntity(registerUserRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User saveUser = userRepository.save(user);
        return userMapper.toRegisterResponse(saveUser);
    }

    @Transactional(readOnly = true)
    public LoginUserRes loginUser(LoginUserReq loginUserRequest) {

        User user = userRepository.findByEmail(loginUserRequest.getEmail()).orElseThrow(()->
                new GlobalException(ResultCode.NOT_FOUND_USER));

        // access 토큰 발급, refresh 토큰 제거
        String accessToken = "Bearer "+jwtAuthenticationProvider.doGenerateAccessToken(user.getEmail(),new HashMap<>());
        RefreshToken.removeUserRefreshToken(accessToken);

        // refresh 토큰 재 발급
        String refreshToken = "Bearer "+ jwtAuthenticationProvider.doGenerateRefreshToken(loginUserRequest.getEmail());
        RefreshToken.putRefreshToken(refreshToken,user.getEmail());

        LoginUserRes res = userMapper.toLoginUserResponse(user);
        res.setAccessToken(accessToken);
        res.setRefreshToken(refreshToken);

        return res;
    }

    @Transactional
    public UpdateUserRes updateUser(UpdateUserReq updateUserRequest) {
        // SCH에서 유저 이메일 정보 획득
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // 유저 정보 확인
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new GlobalException(ResultCode.NOT_FOUND_USER)
        );

        // 새로운 패스워드로 업데이트
        user.updateInfo(updateUserRequest.getName(), updateUserRequest.getNickname());
        return userMapper.toUpdateUserResponse(user);
    }

    @Transactional
    public UpdatePasswordUserRes updatePasswordUser(UpdatePasswordUserReq updatePasswordReq) {
        // SCH에서 유저 이메일 정보 획득
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // 유저 정보 확인
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new GlobalException(ResultCode.NOT_FOUND_USER)
        );

        // 새로운 패스워드로 업데이트
        user.updatePassword(updatePasswordReq.getNewPassword());
        return new UpdatePasswordUserRes(true);
    }

    @Transactional
    public DeleteUserRes deleteUser(DeleteUserReq deleteReq) {
        // SCH에서 유저 이메일 정보 획득
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // 유저 정보 확인
        User user = userRepository.findByEmail(email).orElseThrow( () ->
                new GlobalException(ResultCode.NOT_FOUND_USER) );

        // 유저 삭제
        userRepository.delete(user);
        return new DeleteUserRes(true);
    }

    @Transactional(readOnly = true)
    public GetUserRes getUser(){
        // SCH에서 유저 이메일 정보 획득
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // 유저 정보 확인
        User user = userRepository.findByEmail(email).orElseThrow( () -> new GlobalException(ResultCode.NOT_FOUND_USER));

        return userMapper.toGetUserResponse(user);
    }

}
