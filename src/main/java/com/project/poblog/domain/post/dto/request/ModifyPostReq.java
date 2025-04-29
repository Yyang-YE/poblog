package com.project.poblog.domain.post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Schema(description = "게시글 수정 요청")
@Getter
public class ModifyPostReq {
    @Schema(description = "제목", example = "포트폴리오 작성 팁 (수정)")
    private String title;
    @Schema(description = "내용", example = "열심히 적는다. (수정)")
    private String content;
    @Schema(description = "태그 List", example = "[\"NW\", \"tag\"]")
    List<String> tags;
}
