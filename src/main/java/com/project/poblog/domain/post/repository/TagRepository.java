package com.project.poblog.domain.post.repository;

import com.project.poblog.domain.post.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findByUserIdAndName(Long userId, String tags);
}
