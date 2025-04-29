package com.project.poblog.domain.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdatePasswordReq {
    private String password;
    private String newPassword;
}
