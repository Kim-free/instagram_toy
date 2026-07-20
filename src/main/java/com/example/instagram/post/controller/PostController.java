package com.example.instagram.post.controller;

import com.example.instagram.post.dto.PostCreateRequestDto;
import com.example.instagram.post.dto.PostDeleteRequestDto;
import com.example.instagram.post.dto.PostResponseDto;
import com.example.instagram.post.dto.PostSummaryResponseDto;
import com.example.instagram.post.dto.PostUpdateRequestDto;
import com.example.instagram.post.service.PostService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public PostResponseDto create(@RequestBody PostCreateRequestDto requestDto) {
        return postService.create(requestDto);
    }

    @GetMapping("/{postId}")
    public PostResponseDto get(@PathVariable Long postId) {
        return postService.get(postId);
    }

    @GetMapping
    public List<PostSummaryResponseDto> getAll() {
        return postService.getAll();
    }

    @PatchMapping("/{postId}")
    public PostResponseDto update(
            @PathVariable Long postId,
            @RequestBody PostUpdateRequestDto requestDto
    ) {
        return postService.update(postId, requestDto);
    }

    @DeleteMapping("/{postId}")
    public void delete(
            @PathVariable Long postId,
            @RequestBody PostDeleteRequestDto requestDto
    ) {
        postService.delete(postId, requestDto.getUserId());
    }
}
