package com.example.instagram.postlike.controller;

import com.example.instagram.postlike.dto.PostLikeCountResponseDto;
import com.example.instagram.postlike.dto.PostLikeRequestDto;
import com.example.instagram.postlike.dto.PostLikeResponseDto;
import com.example.instagram.postlike.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/{postId}/likes")
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping
    public PostLikeResponseDto toggle(
            @PathVariable Long postId,
            @RequestBody PostLikeRequestDto requestDto
    ) {
        return postLikeService.toggle(postId, requestDto);
    }

    @GetMapping
    public PostLikeCountResponseDto count(@PathVariable Long postId) {
        return postLikeService.count(postId);
    }
}
