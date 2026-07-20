package com.example.instagram.postlike.dto;

import lombok.*;

@Getter
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class PostLikeCountResponseDto {

    private Long postId;
    private long likeCount;

    public static PostLikeCountResponseDto toDto(Long postId, long likeCount) {
        return PostLikeCountResponseDto.builder()
                .postId(postId)
                .likeCount(likeCount)
                .build();
    }
}
