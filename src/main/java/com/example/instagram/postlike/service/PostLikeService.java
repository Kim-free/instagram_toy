package com.example.instagram.postlike.service;

import com.example.instagram.post.entity.Post;
import com.example.instagram.post.repository.PostRepository;
import com.example.instagram.postlike.dto.PostLikeCountResponseDto;
import com.example.instagram.postlike.dto.PostLikeRequestDto;
import com.example.instagram.postlike.dto.PostLikeResponseDto;
import com.example.instagram.postlike.repository.PostLikeRepository;
import com.example.instagram.user.entity.User;
import com.example.instagram.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public PostLikeResponseDto toggle(Long postId, PostLikeRequestDto requestDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        boolean liked = postLikeRepository.findByUserIdAndPostId(requestDto.getUserId(), postId)
                .map(postLike -> {
                    postLikeRepository.delete(postLike);
                    return false;
                })
                .orElseGet(() -> {
                    postLikeRepository.save(requestDto.toEntity(user, post));
                    return true;
                });

        return PostLikeResponseDto.toDto(postId, liked, postLikeRepository.countByPostId(postId));
    }

    @Transactional(readOnly = true)
    public PostLikeCountResponseDto count(Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }

        return PostLikeCountResponseDto.toDto(postId, postLikeRepository.countByPostId(postId));
    }
}
