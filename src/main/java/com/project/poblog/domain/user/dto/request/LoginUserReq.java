package com.project.poblog.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(description = "로그인 요청")
@Data
@AllArgsConstructor
public class LoginUserReq {
    @Schema(description = "이메일", example = "test@gmail.com")
    private String email;
    @Schema(description = "비밀번호", example = "12345678")
    private String password;
}
