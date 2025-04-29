package com.project.poblog.domain.user.dto;

import com.project.poblog.domain.user.dto.response.*;
import com.project.poblog.domain.user.entity.User;
import com.project.poblog.domain.user.dto.request.JoinUserReq;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring") //스프링 컨텍스트 등록
public interface UserMapper {

    @Mapping(target = "role", expression = "java(com.project.poblog.domain.user.entity.Role.MEMBER)")
    User toEntity(JoinUserReq request);

    JoinUserRes toRegisterResponse(User user);

    @Mapping(target = "refreshToken", ignore = true)
    @Mapping(target = "accessToken", ignore = true)
    LoginUserRes toLoginUserResponse(User user);

    UpdateUserRes toUpdateUserResponse(User user);

    GetUserRes toGetUserResponse(User user);

}
