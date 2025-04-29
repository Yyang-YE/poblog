package com.project.poblog.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(description = "회원 가입 요청")
@Data
@AllArgsConstructor
public class JoinUserReq {
    @Schema(description = "이메일", example = "test@gmail.com")
    private String email;

    @Schema(description = "비밀번호", example = "12345678")
    private String password;

    @Schema(description = "이름", example = "김석원")
    private String name;

    @Schema(description = "닉네임", example = "석원")
    private String nickname;
}
