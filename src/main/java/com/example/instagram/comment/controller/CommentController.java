package com.example.instagram.comment.controller;

import com.example.instagram.comment.dto.CommentCreateRequestDto;
import com.example.instagram.comment.dto.CommentDeleteRequestDto;
import com.example.instagram.comment.dto.CommentResponseDto;
import com.example.instagram.comment.dto.CommentUpdateRequestDto;
import com.example.instagram.comment.dto.ReplyResponseDto;
import com.example.instagram.comment.service.CommentService;
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
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    public CommentResponseDto create(
            @PathVariable Long postId,
            @RequestBody CommentCreateRequestDto requestDto
    ) {
        return commentService.create(postId, requestDto);
    }

    @GetMapping("/posts/{postId}/comments")
    public List<CommentResponseDto> getTopLevelComments(@PathVariable Long postId) {
        return commentService.getTopLevelComments(postId);
    }

    @PostMapping("/comments/{commentId}/replies")
    public ReplyResponseDto createReply(
            @PathVariable Long commentId,
            @RequestBody CommentCreateRequestDto requestDto
    ) {
        return commentService.createReply(commentId, requestDto);
    }

    @GetMapping("/comments/{commentId}/replies")
    public List<ReplyResponseDto> getReplies(@PathVariable Long commentId) {
        return commentService.getReplies(commentId);
    }

    @PatchMapping("/comments/{commentId}")
    public CommentResponseDto update(
            @PathVariable Long commentId,
            @RequestBody CommentUpdateRequestDto requestDto
    ) {
        return commentService.update(commentId, requestDto);
    }

    @DeleteMapping("/comments/{commentId}")
    public void delete(
            @PathVariable Long commentId,
            @RequestBody CommentDeleteRequestDto requestDto
    ) {
        commentService.delete(commentId, requestDto.getUserId());
    }
}
