package com.project.poblog.domain.post.dto.response;

import com.project.poblog.domain.post.entity.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Schema(description = "게시글 단일 조회")
@Getter
public class GetPostRes {
    @Schema(description = "id", example = "1")
    private Long id;
    @Schema(description = "제목", example = "포트폴리오 작성 팁")
    private String title;
    @Schema(description = "내용", example = "열심히 적는다.")
    private String content;
    @Schema(description = "태그 List", example = "[\"네트워크\", \"DB\", \"tag3\"]")
    List<String> tags;

    public GetPostRes(Post post, List<String> tags) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.tags = tags;
    }
}
