package com.example.instagram.commentlike.dto;

import lombok.*;

@Getter
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class CommentLikeResponseDto {

    private Long commentId;
    private boolean liked;
    private long likeCount;

    public static CommentLikeResponseDto toDto(Long commentId, boolean liked, long likeCount) {
        return CommentLikeResponseDto.builder()
                .commentId(commentId)
                .liked(liked)
                .likeCount(likeCount)
                .build();
    }
}
