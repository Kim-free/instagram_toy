package com.example.instagram.post.service;

import com.example.instagram.comment.repository.CommentRepository;
import com.example.instagram.post.dto.PostCreateRequestDto;
import com.example.instagram.post.dto.PostImageResponseDto;
import com.example.instagram.post.dto.PostResponseDto;
import com.example.instagram.post.dto.PostSummaryResponseDto;
import com.example.instagram.post.dto.PostUpdateRequestDto;
import com.example.instagram.post.entity.Post;
import com.example.instagram.post.repository.PostRepository;
import com.example.instagram.postimage.entity.PostImage;
import com.example.instagram.postimage.repository.PostImageRepository;
import com.example.instagram.postlike.repository.PostLikeRepository;
import com.example.instagram.user.entity.User;
import com.example.instagram.user.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostImageRepository postImageRepository;
    private final PostLikeRepository postLikeRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public PostResponseDto create(PostCreateRequestDto requestDto) {
        User author = userRepository.findById(requestDto.getAuthorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Post post = postRepository.save(Post.toEntity(requestDto, author));
        List<PostImage> images = requestDto.getImages() == null
                ? List.of()
                : requestDto.getImages().stream()
                .map(image -> PostImage.toEntity(image, post))
                .toList();
        postImageRepository.saveAll(images);

        return PostResponseDto.toDto(
                post,
                getImages(post.getId()),
                postLikeRepository.countByPostId(post.getId()),
                commentRepository.countByPostId(post.getId())
        );
    }

    @Transactional(readOnly = true)
    public PostResponseDto get(Long postId) {
        Post post = getPost(postId);
        return PostResponseDto.toDto(
                post,
                getImages(post.getId()),
                postLikeRepository.countByPostId(post.getId()),
                commentRepository.countByPostId(post.getId())
        );
    }

    @Transactional(readOnly = true)
    public List<PostSummaryResponseDto> getAll() {
        return postRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(post -> PostSummaryResponseDto.toDto(
                        post,
                        getImages(post.getId()),
                        postLikeRepository.countByPostId(post.getId()),
                        commentRepository.countByPostId(post.getId())
                ))
                .toList();
    }

    @Transactional
    public PostResponseDto update(Long postId, PostUpdateRequestDto requestDto) {
        Post post = getPost(postId);
        validateAuthor(post, requestDto.getUserId());
        post.updateContent(requestDto.getContent());
        return PostResponseDto.toDto(
                post,
                getImages(post.getId()),
                postLikeRepository.countByPostId(post.getId()),
                commentRepository.countByPostId(post.getId())
        );
    }

    @Transactional
    public void delete(Long postId, Long userId) {
        Post post = getPost(postId);
        validateAuthor(post, userId);
        postRepository.delete(post);
    }

    private Post getPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));
    }

    private void validateAuthor(Post post, Long userId) {
        if (!post.getAuthor().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only the author can change this post");
        }
    }

    private List<PostImageResponseDto> getImages(Long postId) {
        return postImageRepository.findByPostIdOrderByDisplayOrderAsc(postId)
                .stream()
                .map(PostImageResponseDto::toDto)
                .toList();
    }
}
