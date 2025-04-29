package com.project.poblog.domain.post.entity;

import com.project.poblog.domain.common.BaseEntity;
import com.project.poblog.domain.post.dto.request.CreatePostReq;
import com.project.poblog.domain.post.dto.request.ModifyPostReq;
import com.project.poblog.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "post")
@Getter
@NoArgsConstructor
public class Post extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder(access = AccessLevel.PRIVATE)
    public Post(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public static Post of(String title, String content, User user) {
        return Post.builder()
            .title(title)
            .content(content)
            .user(user)
            .build();
    }

    public Post(CreatePostReq req, User user) {
        this.title = req.getTitle();
        this.content = req.getContent();
        this.user = user;
    }

    public void update(ModifyPostReq req) {
        this.title = req.getTitle();
        this.content = req.getContent();
    }
}
