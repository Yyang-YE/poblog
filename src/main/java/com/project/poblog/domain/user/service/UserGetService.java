package com.project.poblog.domain.user.service;

import com.project.poblog.domain.user.entity.User;
import com.project.poblog.domain.user.repository.UserRepository;
import com.project.poblog.global.exception.GlobalException;
import com.project.poblog.global.response.ResultCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserGetService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow( () ->
                new GlobalException(ResultCode.NOT_FOUND_USER));
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow( () ->
                new GlobalException(ResultCode.NOT_FOUND_USER));
    }

    public boolean isValidUser(String email, String rawPassword) {
        return userRepository.findByEmail(email)
                .filter(user -> passwordEncoder.matches(rawPassword, user.getPassword()))
                .isPresent();
    }


}
