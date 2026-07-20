package com.example.instagram.commentlike.dto;

import com.example.instagram.comment.entity.Comment;
import com.example.instagram.commentlike.entity.CommentLike;
import com.example.instagram.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentLikeRequestDto {

    private Long userId;

    @Builder
    private CommentLikeRequestDto(Long userId) {
        this.userId = userId;
    }

    public CommentLike toEntity(User user, Comment comment) {
        return CommentLike.builder()
                .user(user)
                .comment(comment)
                .build();
    }
}
