package com.example.instagram.commentlike.controller;

import com.example.instagram.commentlike.dto.CommentLikeCountResponseDto;
import com.example.instagram.commentlike.dto.CommentLikeRequestDto;
import com.example.instagram.commentlike.dto.CommentLikeResponseDto;
import com.example.instagram.commentlike.service.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments/{commentId}/likes")
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @PostMapping
    public CommentLikeResponseDto toggle(
            @PathVariable Long commentId,
            @RequestBody CommentLikeRequestDto requestDto
    ) {
        return commentLikeService.toggle(commentId, requestDto);
    }

    @GetMapping
    public CommentLikeCountResponseDto count(@PathVariable Long commentId) {
        return commentLikeService.count(commentId);
    }
}
