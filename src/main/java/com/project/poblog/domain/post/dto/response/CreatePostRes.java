package com.project.poblog.domain.post.dto.response;

import com.project.poblog.domain.post.entity.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "게시글 생성 완료")
@Getter
public class CreatePostRes {
    private Long postId;

    public CreatePostRes(Post post) {
        this.postId = post.getId();
    }
}
