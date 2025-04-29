package com.project.poblog.domain.post.service;

import com.project.poblog.domain.post.dto.request.CreatePostReq;
import com.project.poblog.domain.post.dto.request.ModifyPostReq;
import com.project.poblog.domain.post.dto.response.CreatePostRes;
import com.project.poblog.domain.post.dto.response.DeletePostRes;
import com.project.poblog.domain.post.dto.response.GetPostRes;
import com.project.poblog.domain.post.dto.response.ModifyPostRes;
import com.project.poblog.domain.post.entity.Post;
import com.project.poblog.domain.post.entity.PostTag;
import com.project.poblog.domain.post.entity.Tag;
import com.project.poblog.domain.post.repository.PostRepository;
import com.project.poblog.domain.post.repository.PostTagRepository;
import com.project.poblog.domain.post.repository.TagRepository;
import com.project.poblog.domain.user.entity.User;
import com.project.poblog.domain.user.repository.UserRepository;
import com.project.poblog.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.project.poblog.global.response.ResultCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final PostTagRepository postTagRepository;

    /*
     * 게시글 생성 : Tag까지 생성
     */
    @Transactional
    public CreatePostRes createPost(CreatePostReq req) {
        // 유저정보 찾기
        User user = findUser();

        // 게시글 저장
        Post post = new Post(req, user);
        postRepository.save(post);

        // 태그를 저장 (DB에 없는 태그들만 새롭게 저장)
        createTag(user, post, req.getTags());

        // 게시글 id 반환
        return new CreatePostRes(post);
    }

    /*
     * 게시글 수정 : Tag까지 수정
     */
    @Transactional
    public ModifyPostRes modifyPost(Long postId, ModifyPostReq req) {
        User user = findUser();
        Post post = findPost(postId);

        // 작성자 확인
        if(post.getUser().getId() != user.getId()) throw new GlobalException(FORBIDDEN);

        post.update(req);

        // 기존 tag 전체 삭제
        List<PostTag> postTags = postTagRepository.findAllByPostId(post.getId());
        postTagRepository.deleteAll(postTags);
        deleteTag(postTags);

        // 새로운 tag 저장
        createTag(user, post, req.getTags());

        return new ModifyPostRes();
    }

    /*
     * 게시글 삭제
     */
    @Transactional
    public DeletePostRes deletePost(Long postId) {
        User user = findUser();
        Post post = findPost(postId);

        // 작성자 확인
        if(post.getUser().getId() != user.getId()) throw new GlobalException(FORBIDDEN);

        // postTag로 tagID 가져오기
        List<PostTag> postTags = postTagRepository.findAllByPostId(postId);

        // postTag 전체 제거
        postTagRepository.deleteAll(postTags);

        // post 제거
        postRepository.deleteById(postId);

        // Tag 제거
        deleteTag(postTags);

        return new DeletePostRes();
    }

    /*
     * 단일 게시글 조회
     */
    @Transactional(readOnly = true)
    public GetPostRes getPost(Long postId) {
        // 유저정보 찾기
        User user = findUser();
        Post post = findPost(postId);

        // 작성자 확인
        if(user.getId() != post.getUser().getId()) throw new GlobalException(FORBIDDEN);

        // 태그 정보 가져오기
        List<String> tags = postTagRepository.findAllByPostId(post.getId()).stream()
            .map(pt -> pt.getTag().getName())
            .toList();

        return new GetPostRes(post, tags);
    }

    private void createTag(User user, Post post, List<String> tags) {
        for (String tName : tags) {
            Tag tag = tagRepository.findByUserIdAndName(user.getId(), tName);
            if(tag == null) { // 없으면 새롭게 저장
                tag = new Tag(tName, user.getId(), 1L);
                tagRepository.save(tag);
            } else { // 있으면 count 증가
                tag.update(tag.getCount() + 1);
            }
            // 완성된 게시글/태그의 id 가져와서 게시글_태그에 저장
            postTagRepository.save(new PostTag(post, tag));
        }
    }

    private void deleteTag(List<PostTag> postTags) {
        for (PostTag pt : postTags) {
            Optional<Tag> optTag = tagRepository.findById(pt.getTag().getId());

            if(optTag.isPresent()) {
                Tag tag = optTag.get();

                if(tag.getCount() == 1) { // 남은 포스트가 없으면 제거
                    tagRepository.delete(tag);
                } else {
                    tag.update(tag.getCount() - 1);
                }
            }
        }
    }

    private User findUser() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByEmail(email).orElseThrow(() -> new GlobalException(NOT_FOUND_USER));
    }

    private Post findPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new GlobalException(NOT_FOUND_POST));
    }
}
