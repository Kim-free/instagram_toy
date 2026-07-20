package com.example.instagram.commentlike.entity;

import com.example.instagram.comment.entity.Comment;
import com.example.instagram.commentlike.dto.CommentLikeRequestDto;
import com.example.instagram.common.entity.CreatedAtEntity;
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
        name = "comment_likes",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_comment_like_user_comment",
                        columnNames = {"user_id", "comment_id"}
                )
        }
)
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class CommentLike extends CreatedAtEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

    public static CommentLike toEntity(CommentLikeRequestDto requestDto, User user, Comment comment) {
        return CommentLike.builder()
                .user(user)
                .comment(comment)
                .build();
    }
}
