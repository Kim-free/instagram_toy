package com.example.instagram.comment.dto;

import com.example.instagram.comment.entity.Comment;
import com.example.instagram.common.util.ElapsedTimeFormatter;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReplyResponseDto {

    private Long commentId;
    private Long parentCommentId;
    private Long postId;
    private Long authorId;
    private String nickname;
    private String content;
    private long likeCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String elapsedTime;

    @Builder
    private ReplyResponseDto(Long commentId, Long parentCommentId, Long postId, Long authorId,
                             String nickname, String content, long likeCount,
                             LocalDateTime createdAt, LocalDateTime updatedAt, String elapsedTime) {
        this.commentId = commentId;
        this.parentCommentId = parentCommentId;
        this.postId = postId;
        this.authorId = authorId;
        this.nickname = nickname;
        this.content = content;
        this.likeCount = likeCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.elapsedTime = elapsedTime;
    }

    public static ReplyResponseDto toDto(Comment comment, long likeCount) {
        Long parentCommentId = comment.getParent() == null ? null : comment.getParent().getId();

        return ReplyResponseDto.builder()
                .commentId(comment.getId())
                .parentCommentId(parentCommentId)
                .postId(comment.getPost().getId())
                .authorId(comment.getAuthor().getId())
                .nickname(comment.getAuthor().getNickname())
                .content(comment.getContent())
                .likeCount(likeCount)
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .elapsedTime(ElapsedTimeFormatter.format(comment.getCreatedAt()))
                .build();
    }
}
