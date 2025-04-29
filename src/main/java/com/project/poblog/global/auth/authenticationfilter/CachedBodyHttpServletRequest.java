package com.project.poblog.global.auth.authenticationfilter;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.io.ByteArrayInputStream;


/**
 * HttpServletRequest의 InputStream을 여러 번 읽을 수 있도록 캐싱하는 래퍼 클래스
 * (한 번 읽으면 소모되는 기본 스트림의 한계를 극복하기 위해 사용)
 */
public class CachedBodyHttpServletRequest extends HttpServletRequestWrapper {

    private final byte[] cachedBody;

    /**
     * 요청(Request) 본문을 미리 읽어와서 저장하는 생성자
     *
     * @param request 원본 HttpServletRequest
     * @param cachedBody 읽어온 본문(byte 배열)
     */
    public CachedBodyHttpServletRequest(HttpServletRequest request, byte[] cachedBody) {
        super(request);
        this.cachedBody = cachedBody;
    }


    /**
     * 저장된 cachedBody를 InputStream 형태로 반환
     * 필터나 컨트롤러에서 요청 본문을 다시 읽을 수 있게 함
     */
    @Override
    public ServletInputStream getInputStream() {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(cachedBody);
        return new ServletInputStream() {

            // 읽을 데이터가 더 이상 없는지 여부 반환
            @Override
            public boolean isFinished() {
                return byteArrayInputStream.available() == 0;
            }

            // 항상 읽을 준비가 되어 있다고 반환
            @Override
            public boolean isReady() {
                return true;
            }

            // 비동기 처리가 필요 없으므로 구현 비워둠
            @Override
            public void setReadListener(ReadListener readListener) {
            }

            // 다음 바이트를 읽어 반환
            @Override
            public int read(){
                return byteArrayInputStream.read();
            }
        };
    }
}
