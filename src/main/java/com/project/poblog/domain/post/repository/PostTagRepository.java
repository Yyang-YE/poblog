package com.project.poblog.domain.post.repository;

import com.project.poblog.domain.post.entity.Post;
import com.project.poblog.domain.post.entity.PostTag;
import com.project.poblog.domain.post.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostTagRepository extends JpaRepository<PostTag, Long> {
    List<PostTag> post(Post post);

    List<PostTag> findAllByPostId(Long postId);
}
