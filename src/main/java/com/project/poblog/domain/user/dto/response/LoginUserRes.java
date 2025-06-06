package com.project.poblog.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserRes {
    private String nickname;
    private String accessToken;
    private String refreshToken;
}
