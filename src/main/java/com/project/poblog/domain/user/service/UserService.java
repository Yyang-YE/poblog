package com.project.poblog.domain.user.service;

import com.project.poblog.domain.user.entity.User;
import com.project.poblog.domain.user.dto.UserMapper;
import com.project.poblog.domain.user.dto.request.LoginReq;
import com.project.poblog.domain.user.dto.request.JoinReq;
import com.project.poblog.domain.user.dto.response.LoginRes;
import com.project.poblog.domain.user.dto.response.JoinRes;
import com.project.poblog.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    public JoinRes join(JoinReq registerUserRequest) {
        User user = userMapper.toEntity(registerUserRequest);
        User saveUser = userRepository.save(user);
        return userMapper.toRegisterResponse(saveUser);
    }

    public LoginRes Login(LoginReq loginUserRequest) {
        User user = userRepository.findByEmail(loginUserRequest.getEmail()).orElseThrow(null);
        return userMapper.toLoginUserResponse(user);
    }
}
