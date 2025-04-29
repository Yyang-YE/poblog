package com.project.poblog.global.auth.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * 요청(Request) 본문(body)을 읽어 JSON 파싱을 도와주는 유틸리티 클래스
 */
public class JsonRequestBodyUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * HttpServletRequest로부터 요청 본문 전체를 byte 배열로 읽어오는 메서드
     *
     * @param request HttpServletRequest 객체
     * @return 요청 본문을 byte[]로 반환
     * @throws IOException 입출력 예외 발생 시
     */
    public static byte[] extractRequestBody(HttpServletRequest request) throws IOException {
        return request.getInputStream().readAllBytes();
    }

    /**
     * 요청 본문(byte 배열)을 Map<String, String> 형태로 파싱하는 메서드
     *
     * @param bodyBytes 요청 본문을 byte[]로 전달
     * @return JSON 데이터를 Map으로 변환한 결과, 실패 시 null 반환
     */
    public static Map<String, String> parseToMap(byte[] bodyBytes) {
        try {
            String body = new String(bodyBytes, StandardCharsets.UTF_8);
            return objectMapper.readValue(body, new TypeReference<>() {});
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 요청 본문(byte 배열)을 특정 클래스 객체로 파싱하는 메서드
     *
     * @param bodyBytes 요청 본문을 byte[]로 전달
     * @param clazz 변환할 대상 클래스 타입
     * @param <T> 반환 타입
     * @return JSON 데이터를 클래스 객체로 변환한 결과, 실패 시 null 반환
     */
    public static <T> T parseToObject(byte[] bodyBytes, Class<T> clazz) {
        try {
            String body = new String(bodyBytes, StandardCharsets.UTF_8);
            return objectMapper.readValue(body, clazz);
        } catch (IOException e) {
            return null;
        }
    }
}
