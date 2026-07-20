package com.example.instagram.post.dto;

import com.example.instagram.postimage.entity.PostImage;
import lombok.*;

@Getter
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class PostImageResponseDto {

    private Long imageId;
    private String imageKey;
    private Integer displayOrder;

    public static PostImageResponseDto toDto(PostImage postImage) {
        return PostImageResponseDto.builder()
                .imageId(postImage.getId())
                .imageKey(postImage.getImageKey())
                .displayOrder(postImage.getDisplayOrder())
                .build();
    }
}
