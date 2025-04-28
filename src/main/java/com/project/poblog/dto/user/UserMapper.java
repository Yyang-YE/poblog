package com.project.poblog.dto.user;

import com.project.poblog.domain.user.entity.User;
import com.project.poblog.dto.user.request.JoinReq;
import com.project.poblog.dto.user.response.LoginRes;
import com.project.poblog.dto.user.response.JoinRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring") //스프링 컨텍스트 등록
public interface UserMapper {

    @Mapping(target = "role", expression = "java(com.project.poblog.domain.user.entity.Role.MEMBER)")
    User toEntity(JoinReq request);

    JoinRes toRegisterResponse(User user);
    LoginRes toLoginUserResponse(User user);
}
