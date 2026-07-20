package com.example.instagram.postlike.dto;

import com.example.instagram.post.entity.Post;
import com.example.instagram.postlike.entity.PostLike;
import com.example.instagram.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostLikeRequestDto {

    private Long userId;

    @Builder
    private PostLikeRequestDto(Long userId) {
        this.userId = userId;
    }

    public PostLike toEntity(User user, Post post) {
        return PostLike.builder()
                .user(user)
                .post(post)
                .build();
    }
}
