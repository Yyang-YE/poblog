package com.project.poblog.domain.post.entity;

import com.project.poblog.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "post_tag")
@NoArgsConstructor
public class PostTag extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @Builder(access = AccessLevel.PRIVATE)
    public PostTag(Post post, Tag tag) {
        this.post = post;
        this.tag = tag;
    }

    public static PostTag of(Post post, Tag tag) {
        return PostTag.builder()
            .post(post)
            .tag(tag)
            .build();
    }
}
