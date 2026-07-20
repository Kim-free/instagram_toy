package com.example.instagram.postimage.entity;

import com.example.instagram.common.entity.BaseEntity;
import com.example.instagram.post.entity.Post;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostImage extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(nullable = false)
    private String imageKey;

    @Column(nullable = false)
    private Integer displayOrder;

    @Builder
    private PostImage(Post post, String imageKey, Integer displayOrder) {
        this.post = post;
        this.imageKey = imageKey;
        this.displayOrder = displayOrder;
    }
}
