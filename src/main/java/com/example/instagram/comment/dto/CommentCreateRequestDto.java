package com.example.instagram.comment.dto;

import lombok.*;

@Getter
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class CommentCreateRequestDto {

    private Long authorId;
    private String content;
}
