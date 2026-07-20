package com.example.instagram.comment.dto;

import lombok.*;

@Getter
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class CommentUpdateRequestDto {

    private Long userId;
    private String content;
}
