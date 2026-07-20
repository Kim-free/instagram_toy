package com.example.instagram.postlike.entity;

import com.example.instagram.common.entity.CreatedAtEntity;
import com.example.instagram.post.entity.Post;
import com.example.instagram.postlike.dto.PostLikeRequestDto;
import com.example.instagram.user.entity.User;
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
        name = "post_likes",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_post_like_user_post",
                        columnNames = {"user_id", "post_id"}
                )
        }
)
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class PostLike extends CreatedAtEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    public static PostLike toEntity(PostLikeRequestDto requestDto, User user, Post post) {
        return PostLike.builder()
                .user(user)
                .post(post)
                .build();
    }
}
