package com.example.instagram.comment.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentUpdateRequestDto {

    private Long userId;
    private String content;

    @Builder
    private CommentUpdateRequestDto(Long userId, String content) {
        this.userId = userId;
        this.content = content;
    }
}
