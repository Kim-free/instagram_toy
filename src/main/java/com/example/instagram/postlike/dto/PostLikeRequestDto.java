package com.example.instagram.postlike.dto;

import lombok.*;

@Getter
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class PostLikeRequestDto {

    private Long userId;
}
