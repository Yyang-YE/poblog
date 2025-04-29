package com.project.poblog.domain.post.entity;

import com.project.poblog.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "tag")
@NoArgsConstructor
public class Tag extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "tag_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "count")
    private Long count;

    @Builder(access = AccessLevel.PRIVATE)
    public Tag(String name, Long userId) {
        this.name = name;
        this.userId = userId;
    }

    public static Tag of(String name, Long userId) {
        return Tag.builder()
            .name(name)
            .userId(userId)
            .build();
    }

    public Tag (String name, Long userId, Long count) {
        this.name = name;
        this.userId = userId;
        this.count = count;
    }

    public void update(Long count) {
        this.count = count;
    }
}
