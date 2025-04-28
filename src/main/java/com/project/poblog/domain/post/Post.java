package com.project.poblog.domain.post;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Post {
    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;
}
