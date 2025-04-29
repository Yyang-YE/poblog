package com.project.poblog.domain.user.controller;

import com.project.poblog.domain.user.dto.request.*;
import com.project.poblog.domain.user.dto.response.*;
import com.project.poblog.global.response.CommonResponse;
import com.project.poblog.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Operation(summary = "회원가입", description = "회원가입 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "정상 처리 되었습니다."),
    })
    @PostMapping("/join")
    public CommonResponse<JoinUserRes> join(@RequestBody JoinUserReq joinReq) {
        return CommonResponse.success(userService.joinUser(joinReq));
    }


    @Operation(summary = "로그인", description = "로그인 시 JWT 발급")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "정상 처리 되었습니다."),
            @ApiResponse(responseCode = "400", description = "입력이 잘못되었습니다.",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
    })
    @PostMapping("/login")
    public CommonResponse<LoginUserRes> login(@RequestBody LoginUserReq loginReq) {
        return CommonResponse.success(userService.loginUser(loginReq));
    }


    @Operation(summary = "회원 정보 수정", description = "회원 정보 수정: 로그인 필요")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "정상 처리 되었습니다."),
            @ApiResponse(responseCode = "400", description = "입력이 잘못되었습니다.",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
    })
    @PutMapping("/update")
    public CommonResponse<UpdateUserRes> update(@RequestBody UpdateUserReq updateReq) {
        return CommonResponse.success(userService.updateUser(updateReq));
    }

    @Operation(summary = "회원 비밀번호 수정", description = "회원 비밀번호 수정: 로그인 필요, 비밀번호 한번 더 입력")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "정상 처리 되었습니다."),
            @ApiResponse(responseCode = "400", description = "입력이 잘못되었습니다.",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
    })
    @PutMapping("/update/password")
    public CommonResponse<UpdatePasswordUserRes> updatePassword(@RequestBody UpdatePasswordUserReq updatePasswordReq) {
        return CommonResponse.success(userService.updatePasswordUser(updatePasswordReq));
    }

    @Operation(summary = "회원 정보 조회", description = "회원 정보 조회: 로그인 필요")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "정상 처리 되었습니다."),
            @ApiResponse(responseCode = "400", description = "입력이 잘못되었습니다.",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
    })
    @GetMapping("/get")
    public CommonResponse<GetUserRes> getUser() {
        return CommonResponse.success(userService.getUser());
    }

    @Operation(summary = "회원 삭제", description = "회원 삭제: 로그인 필요, 비밀번호 한번 더 입력")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "정상 처리 되었습니다."),
            @ApiResponse(responseCode = "400", description = "입력이 잘못되었습니다.",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
    })
    @PostMapping("/delete")
    public CommonResponse<DeleteUserRes> delete(@RequestBody DeleteUserReq deleteUserReq) {
        return CommonResponse.success(userService.deleteUser(deleteUserReq));
    }
}