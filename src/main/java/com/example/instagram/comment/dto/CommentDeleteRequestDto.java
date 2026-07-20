package com.example.instagram.comment.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentDeleteRequestDto {

    private Long userId;

    @Builder
    private CommentDeleteRequestDto(Long userId) {
        this.userId = userId;
    }
}
