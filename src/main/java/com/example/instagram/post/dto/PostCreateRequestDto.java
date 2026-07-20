package com.example.instagram.post.dto;

import java.util.List;
import lombok.*;

@Getter
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class PostCreateRequestDto {

    private Long authorId;
    private String content;
    private List<PostImageRequestDto> images;
}
