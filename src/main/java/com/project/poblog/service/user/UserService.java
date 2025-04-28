package com.project.poblog.service.user;

import com.project.poblog.domain.user.entity.User;
import com.project.poblog.dto.user.UserMapper;
import com.project.poblog.dto.user.request.LoginReq;
import com.project.poblog.dto.user.request.JoinReq;
import com.project.poblog.dto.user.response.LoginRes;
import com.project.poblog.dto.user.response.JoinRes;
import com.project.poblog.global.exception.GlobalException;
import com.project.poblog.global.response.ResultCode;
import com.project.poblog.repository.user.UserRepository;
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
        User user = userRepository.findByEmail(loginUserRequest.getEmail()).orElseThrow(()->
                new GlobalException(ResultCode.INVALID_INPUT));
        return userMapper.toLoginUserResponse(user);
    }


}
