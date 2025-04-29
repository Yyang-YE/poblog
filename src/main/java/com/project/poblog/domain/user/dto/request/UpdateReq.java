package com.project.poblog.domain.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateReq {
    private String name;
    private String nickname;
}
