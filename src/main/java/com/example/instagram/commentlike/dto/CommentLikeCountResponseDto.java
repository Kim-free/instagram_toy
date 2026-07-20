package com.example.instagram.commentlike.dto;

import lombok.*;

@Getter
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class CommentLikeCountResponseDto {

    private Long commentId;
    private long likeCount;

    public static CommentLikeCountResponseDto toDto(Long commentId, long likeCount) {
        return CommentLikeCountResponseDto.builder()
                .commentId(commentId)
                .likeCount(likeCount)
                .build();
    }
}
