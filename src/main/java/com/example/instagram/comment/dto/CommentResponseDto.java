package com.example.instagram.comment.dto;

import com.example.instagram.comment.entity.Comment;
import com.example.instagram.common.util.ElapsedTimeFormatter;
import java.time.LocalDateTime;
import lombok.*;

@Getter
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
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
