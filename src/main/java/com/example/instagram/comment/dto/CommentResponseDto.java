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
public class CommentResponseDto {

    private Long commentId;
    private Long postId;
    private Long authorId;
    private String nickname;
    private String content;
    private long likeCount;
    private long replyCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String elapsedTime;

    @Builder
    private CommentResponseDto(Long commentId, Long postId, Long authorId, String nickname,
                               String content, long likeCount, long replyCount,
                               LocalDateTime createdAt, LocalDateTime updatedAt, String elapsedTime) {
        this.commentId = commentId;
        this.postId = postId;
        this.authorId = authorId;
        this.nickname = nickname;
        this.content = content;
        this.likeCount = likeCount;
        this.replyCount = replyCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.elapsedTime = elapsedTime;
    }

    public static CommentResponseDto toDto(Comment comment, long likeCount, long replyCount) {
        return CommentResponseDto.builder()
                .commentId(comment.getId())
                .postId(comment.getPost().getId())
                .authorId(comment.getAuthor().getId())
                .nickname(comment.getAuthor().getNickname())
                .content(comment.getContent())
                .likeCount(likeCount)
                .replyCount(replyCount)
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .elapsedTime(ElapsedTimeFormatter.format(comment.getCreatedAt()))
                .build();
    }
}
