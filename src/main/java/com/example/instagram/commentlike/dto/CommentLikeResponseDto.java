package com.example.instagram.commentlike.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentLikeResponseDto {

    private Long commentId;
    private boolean liked;
    private long likeCount;

    @Builder
    private CommentLikeResponseDto(Long commentId, boolean liked, long likeCount) {
        this.commentId = commentId;
        this.liked = liked;
        this.likeCount = likeCount;
    }

    public static CommentLikeResponseDto toDto(Long commentId, boolean liked, long likeCount) {
        return CommentLikeResponseDto.builder()
                .commentId(commentId)
                .liked(liked)
                .likeCount(likeCount)
                .build();
    }
}
