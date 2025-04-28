package com.project.poblog.dto.user;

import com.project.poblog.domain.user.Role;
import com.project.poblog.domain.user.User;
import com.project.poblog.dto.user.request.JoinReq;
import com.project.poblog.dto.user.response.JoinRes;
import com.project.poblog.dto.user.response.LoginRes;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-28T11:26:29+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.14 (Homebrew)"
)
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(JoinReq request) {
        if ( request == null ) {
            return null;
        }

        String email = null;
        String password = null;
        String name = null;
        String nickname = null;

        email = request.getEmail();
        password = request.getPassword();
        name = request.getName();
        nickname = request.getNickname();

        Role role = com.project.poblog.domain.user.Role.MEMBER;

        User user = new User( email, password, name, nickname, role );

        return user;
    }

    @Override
    public JoinRes toRegisterResponse(User user) {
        if ( user == null ) {
            return null;
        }

        JoinRes joinRes = new JoinRes();

        joinRes.setEmail( user.getEmail() );
        joinRes.setNickname( user.getNickname() );

        return joinRes;
    }

    @Override
    public LoginRes toLoginUserResponse(User user) {
        if ( user == null ) {
            return null;
        }

        LoginRes loginRes = new LoginRes();

        loginRes.setNickname( user.getNickname() );

        return loginRes;
    }
}
