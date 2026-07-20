package com.example.instagram.post.dto;

import lombok.*;

@Getter
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class PostDeleteRequestDto {

    private Long userId;
}
