package com.example.instagram.postimage.entity;

import com.example.instagram.common.entity.BaseEntity;
import com.example.instagram.post.dto.PostImageRequestDto;
import com.example.instagram.post.entity.Post;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.*;

@Getter
@Entity
@Table(
        name = "post_images",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_post_image_post_display_order",
                        columnNames = {"post_id", "display_order"}
                )
        }
)
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class PostImage extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(nullable = false)
    private String imageKey;

    @Column(nullable = false)
    private Integer displayOrder;

    public static PostImage toEntity(PostImageRequestDto requestDto, Post post) {
        return PostImage.builder()
                .post(post)
                .imageKey(requestDto.getImageKey())
                .displayOrder(requestDto.getDisplayOrder())
                .build();
    }
}
