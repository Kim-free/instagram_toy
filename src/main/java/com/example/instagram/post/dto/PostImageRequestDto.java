package com.example.instagram.post.dto;

import lombok.*;

@Getter
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class PostImageRequestDto {

    private String imageKey;
    private Integer displayOrder;
}
