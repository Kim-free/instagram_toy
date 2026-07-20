package com.example.instagram.comment.dto;

import lombok.*;

@Getter
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class CommentDeleteRequestDto {

    private Long userId;
}
