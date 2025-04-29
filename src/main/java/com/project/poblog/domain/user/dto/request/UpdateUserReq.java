package com.project.poblog.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(description = "유저 정보 변경 요청")
@Data
@AllArgsConstructor
public class UpdateUserReq {
    @Schema(description = "새로운 이름", example = "김원석")
    private String name;
    @Schema(description = "새로운 닉네임", example = "원석")
    private String nickname;
}
