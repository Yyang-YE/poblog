package com.project.poblog.domain.post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Schema(description = "게시글 생성 요청")
@Getter
public class CreatePostReq {
    @Schema(description = "제목", example = "포트폴리오 작성 팁")
    private String title;
    @Schema(description = "내용", example = "열심히 적는다.")
    private String content;
    @Schema(description = "태그 List", example = "[\"네트워크\", \"DB\", \"tag3\"]")
    List<String> tags;
}
