package com.example.instagram.post.dto;

import lombok.*;

@Getter
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class PostUpdateRequestDto {

    private Long userId;
    private String content;
}
