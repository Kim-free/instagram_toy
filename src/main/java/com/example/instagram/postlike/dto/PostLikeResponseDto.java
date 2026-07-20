package com.example.instagram.postlike.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostLikeResponseDto {

    private Long postId;
    private boolean liked;
    private long likeCount;

    @Builder
    private PostLikeResponseDto(Long postId, boolean liked, long likeCount) {
        this.postId = postId;
        this.liked = liked;
        this.likeCount = likeCount;
    }

    public static PostLikeResponseDto toDto(Long postId, boolean liked, long likeCount) {
        return PostLikeResponseDto.builder()
                .postId(postId)
                .liked(liked)
                .likeCount(likeCount)
                .build();
    }
}
