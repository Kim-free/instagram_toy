package com.example.instagram.post.dto;

import com.example.instagram.post.entity.Post;
import com.example.instagram.postimage.entity.PostImage;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostImageRequestDto {

    private String imageKey;
    private Integer displayOrder;

    @Builder
    private PostImageRequestDto(String imageKey, Integer displayOrder) {
        this.imageKey = imageKey;
        this.displayOrder = displayOrder;
    }

    public PostImage toEntity(Post post) {
        return PostImage.builder()
                .post(post)
                .imageKey(imageKey)
                .displayOrder(displayOrder)
                .build();
    }
}
