package com.example.instagram.postlike.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostLikeCountResponseDto {

    private Long postId;
    private long likeCount;

    @Builder
    private PostLikeCountResponseDto(Long postId, long likeCount) {
        this.postId = postId;
        this.likeCount = likeCount;
    }

    public static PostLikeCountResponseDto toDto(Long postId, long likeCount) {
        return PostLikeCountResponseDto.builder()
                .postId(postId)
                .likeCount(likeCount)
                .build();
    }
}
