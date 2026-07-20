package com.example.instagram.commentlike.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentLikeCountResponseDto {

    private Long commentId;
    private long likeCount;

    @Builder
    private CommentLikeCountResponseDto(Long commentId, long likeCount) {
        this.commentId = commentId;
        this.likeCount = likeCount;
    }

    public static CommentLikeCountResponseDto toDto(Long commentId, long likeCount) {
        return CommentLikeCountResponseDto.builder()
                .commentId(commentId)
                .likeCount(likeCount)
                .build();
    }
}
