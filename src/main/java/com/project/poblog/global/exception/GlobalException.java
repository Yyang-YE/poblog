package com.project.poblog.global.exception;

import com.project.poblog.global.response.ResultCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GlobalException extends RuntimeException {
    private final ResultCode resultCode;
}
