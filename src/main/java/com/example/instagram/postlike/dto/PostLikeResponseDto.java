package com.example.instagram.postlike.dto;

import lombok.*;

@Getter
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class PostLikeResponseDto {

    private Long postId;
    private boolean liked;
    private long likeCount;

    public static PostLikeResponseDto toDto(Long postId, boolean liked, long likeCount) {
        return PostLikeResponseDto.builder()
                .postId(postId)
                .liked(liked)
                .likeCount(likeCount)
                .build();
    }
}
