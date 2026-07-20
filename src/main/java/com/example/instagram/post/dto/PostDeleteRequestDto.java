package com.example.instagram.post.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostDeleteRequestDto {

    private Long userId;

    @Builder
    private PostDeleteRequestDto(Long userId) {
        this.userId = userId;
    }
}
