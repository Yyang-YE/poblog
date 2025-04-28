package com.project.poblog.global.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ResultCode {
    // 글로벌 1000번대
    SUCCESS(HttpStatus.OK, 0, "정상 처리 되었습니다."),
    INVALID_INPUT(HttpStatus.BAD_REQUEST, 1000, "잘못된 입력값입니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, 1001, "유효하지 않은 토큰입니다."), // 로그인 관련
    REFRESH_TOKEN_REQUIRED(HttpStatus.UNAUTHORIZED, 1002, "Refresh Token이 필요합니다."), // 로그인 관련
    LOG_IN_REQUIRED(HttpStatus.UNAUTHORIZED, 1004, "다시 로그인 해주세요."), // 로그인 관련
    SYSTEM_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 1005, "서버 시스템 문제가 발생했습니다."),
    ILLEGAL_REGISTRATION_ID(HttpStatus.BAD_REQUEST, 1006, "지원하는 소셜이 아닙니다."),
    INVALID_IMAGE_FILE(HttpStatus.BAD_REQUEST, 1007, "지원하는 파일 형식이 아닙니다."),
    MAXIMUM_UPLOAD_FILE_SIZE(HttpStatus.BAD_REQUEST, 1008, "파일 크기는 최대 10MB까지 가능합니다."),
    NOT_FOUND_FILE(HttpStatus.NOT_FOUND, 1009, "파일을 찾을 수 없습니다."),
    REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, 1010, "Refresh Token이 만료되었습니다."), // 로그인 관련
    DELETED_USER(HttpStatus.UNAUTHORIZED, 1011, "탈퇴한 사용자입니다."), // 로그인 관련
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, 1012, "인증이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, 1013, "권한이 없는 사용자입니다."),
    DB_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 1014, "DB 데이터 문제가 발생했습니다."),
    UNSUPPORTED_REQUEST(HttpStatus.NOT_FOUND, 1015, "존재하지 않는 요청입니다."),

    // 2000번대
    TEST_ERROR(HttpStatus.BAD_REQUEST, 2000, "예외 테스트입니다.")

    ;

    private final HttpStatus status;
    private final int code;
    private final String message;
}
