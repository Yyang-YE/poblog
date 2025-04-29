package com.project.poblog.domain.post.controller;

import com.project.poblog.domain.post.dto.request.CreatePostReq;
import com.project.poblog.domain.post.dto.request.ModifyPostReq;
import com.project.poblog.domain.post.dto.response.CreatePostRes;
import com.project.poblog.domain.post.dto.response.DeletePostRes;
import com.project.poblog.domain.post.dto.response.GetPostRes;
import com.project.poblog.domain.post.dto.response.ModifyPostRes;
import com.project.poblog.domain.post.service.PostService;
import com.project.poblog.global.auth.authenticationprovider.JwtAuthenticationProvider;
import com.project.poblog.global.response.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    /**
     * 게시글 생성
     * @param req 게시글 생성을 위한 정보
     */
    @Operation(summary = "게시글 생성", description = "게시글 생성: 로그인 필요")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "정상 처리 되었습니다."),
        @ApiResponse(responseCode = "400", description = "입력이 잘못되었습니다.",
            content = @Content(schema = @Schema(implementation = CommonResponse.class))),
        @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다.",
            content = @Content(schema = @Schema(implementation = CommonResponse.class))),
    })
    @PostMapping
    public CommonResponse<CreatePostRes> createPost(
        @RequestBody CreatePostReq req) {
        return CommonResponse.success(postService.createPost(req));
    }

    /**
     * 게시글 수정
     * @param postId 게시글 ID
     * @param req 게시글 수정을 위한 정보
     */
    @Operation(summary = "게시글 수정", description = "게시글 수정: 로그인 필요")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "정상 처리 되었습니다."),
        @ApiResponse(responseCode = "400", description = "입력이 잘못되었습니다.",
            content = @Content(schema = @Schema(implementation = CommonResponse.class))),
        @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다.",
            content = @Content(schema = @Schema(implementation = CommonResponse.class))),
    })
    @PostMapping("/{postId}")
    public CommonResponse<ModifyPostRes> modifyPost(
        @Parameter(name = "postId", description = "게시글 id", example = "1", required = true)
        @PathVariable Long postId,
        @RequestBody ModifyPostReq req) {
        return CommonResponse.success(postService.modifyPost(postId, req));
    }

    /**
     * 게시글 삭제
     * @param postId 게시글 ID
     */
    @Operation(summary = "게시글 삭제", description = "게시글 삭제: 로그인 필요")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "정상 처리 되었습니다."),
        @ApiResponse(responseCode = "400", description = "입력이 잘못되었습니다.",
            content = @Content(schema = @Schema(implementation = CommonResponse.class))),
        @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다.",
            content = @Content(schema = @Schema(implementation = CommonResponse.class))),
    })
    @DeleteMapping("/{postId}")
    public CommonResponse<DeletePostRes> deletePost(
        @Parameter(name = "postId", description = "게시글 id", example = "1", required = true)
        @PathVariable Long postId) {
        return CommonResponse.success(postService.deletePost(postId));
    }

    /**
     * 게시글 조회
     * @param postId 게시글 ID
     */
    @Operation(summary = "게시글 조회", description = "게시글 조회: 로그인 필요")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "정상 처리 되었습니다."),
        @ApiResponse(responseCode = "400", description = "입력이 잘못되었습니다.",
            content = @Content(schema = @Schema(implementation = CommonResponse.class))),
        @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다.",
            content = @Content(schema = @Schema(implementation = CommonResponse.class))),
    })
    @GetMapping("/{postId}")
    public CommonResponse<GetPostRes> getPost(
        @Parameter(name = "postId", description = "게시글 id", example = "1", required = true)
        @PathVariable Long postId) {
        return CommonResponse.success(postService.getPost(postId));
    }
}
