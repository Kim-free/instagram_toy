package com.example.instagram.commentlike.dto;

import lombok.*;

@Getter
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class CommentLikeRequestDto {

    private Long userId;
}
