package com.project.poblog.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(description = "비밀번호 변경 요청")
@Data
@AllArgsConstructor
public class UpdatePasswordUserReq {

    @Schema(description = "현재 비밀번호", example = "12345678")
    private String password;

    @Schema(description = "새로운 비밀번호", example = "abcdefg")
    private String newPassword;
}
