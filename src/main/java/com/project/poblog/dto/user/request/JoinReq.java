package com.project.poblog.dto.user.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JoinReq {

    private String email;
    private String password;
    private String name;
    private String nickname;
}
