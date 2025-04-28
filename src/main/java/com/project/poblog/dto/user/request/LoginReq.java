package com.project.poblog.dto.user.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginReq {
    private String email;
    private String password;
}
