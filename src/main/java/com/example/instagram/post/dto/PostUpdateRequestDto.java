package com.example.instagram.post.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostUpdateRequestDto {

    private Long userId;
    private String content;

    @Builder
    private PostUpdateRequestDto(Long userId, String content) {
        this.userId = userId;
        this.content = content;
    }
}
