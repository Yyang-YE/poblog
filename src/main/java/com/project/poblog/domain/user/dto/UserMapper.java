package com.project.poblog.domain.user.dto;

import com.project.poblog.domain.user.dto.request.UpdatePasswordReq;
import com.project.poblog.domain.user.dto.response.UpdatePasswordRes;
import com.project.poblog.domain.user.dto.response.UpdateRes;
import com.project.poblog.domain.user.entity.User;
import com.project.poblog.domain.user.dto.request.JoinReq;
import com.project.poblog.domain.user.dto.response.LoginRes;
import com.project.poblog.domain.user.dto.response.JoinRes;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring") //스프링 컨텍스트 등록
public interface UserMapper {

    @Mapping(target = "role", expression = "java(com.project.poblog.domain.user.entity.Role.MEMBER)")
    User toEntity(JoinReq request);

    JoinRes toRegisterResponse(User user);

    @Mapping(target = "refreshToken", ignore = true)
    @Mapping(target = "accessToken", ignore = true)
    LoginRes toLoginUserResponse(User user);

    UpdateRes toUpdateUserResponse(User user);

    @Mapping(target = "success", ignore = true)
    UpdatePasswordRes toUpdatePasswordResponse(User user);

    @AfterMapping
    default void updatePassword(@MappingTarget UpdatePasswordRes dto) {
        dto.setSuccess(true);
    }

}
