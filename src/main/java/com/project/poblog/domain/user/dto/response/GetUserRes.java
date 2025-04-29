package com.project.poblog.domain.user.dto.response;

import lombok.Data;

@Data
public class GetUserRes {
    private String email;
    private String name;
    private String nickname;
    private String role;
}
