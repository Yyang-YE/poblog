package com.project.poblog.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(description = "회원 삭제 요청")
@Data
@AllArgsConstructor
public class DeleteUserReq {
    String password;
}
