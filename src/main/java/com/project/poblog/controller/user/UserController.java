package com.project.poblog.controller.user;

import com.project.poblog.domain.user.dto.request.JoinReq;
import com.project.poblog.domain.user.dto.request.LoginReq;
import com.project.poblog.domain.user.dto.request.UpdateReq;
import com.project.poblog.domain.user.dto.response.JoinRes;
import com.project.poblog.domain.user.dto.response.LoginRes;
import com.project.poblog.domain.user.dto.response.UpdateRes;
import com.project.poblog.global.response.CommonResponse;
import com.project.poblog.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    // 회원가입 (완료 시 로그인 창으로 이동, 토큰X)
    @Operation(summary = "회원가입", description = "회원가입 API")
    @ApiResponses(value = { // 발생 가능 오류들 넣어두는 역할..?
            @ApiResponse(responseCode = "200", description = "정상 처리 되었습니다."),
    })
    @PostMapping("/join")
    public CommonResponse<JoinRes> join(@RequestBody JoinReq joinReq) {
        return CommonResponse.success(userService.join(joinReq));
    }

    @PostMapping("/login")
    public CommonResponse<LoginRes> login(@RequestBody LoginReq loginReq) {
        return CommonResponse.success(userService.login(loginReq));
    }

    @PutMapping("/update")
    public CommonResponse<UpdateRes> update(@RequestBody UpdateReq updateReq) {
        return CommonResponse.success(userService.update(updateReq));
    }
}