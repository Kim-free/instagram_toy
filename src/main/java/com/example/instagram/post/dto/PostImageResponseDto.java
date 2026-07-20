package com.example.instagram.post.dto;

import com.example.instagram.postimage.entity.PostImage;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostImageResponseDto {

    private Long imageId;
    private String imageKey;
    private Integer displayOrder;

    @Builder
    private PostImageResponseDto(Long imageId, String imageKey, Integer displayOrder) {
        this.imageId = imageId;
        this.imageKey = imageKey;
        this.displayOrder = displayOrder;
    }

    public static PostImageResponseDto toDto(PostImage postImage) {
        return PostImageResponseDto.builder()
                .imageId(postImage.getId())
                .imageKey(postImage.getImageKey())
                .displayOrder(postImage.getDisplayOrder())
                .build();
    }
}
