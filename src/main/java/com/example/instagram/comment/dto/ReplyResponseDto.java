package com.example.instagram.comment.dto;

import com.example.instagram.comment.entity.Comment;
import com.example.instagram.common.util.ElapsedTimeFormatter;
import java.time.LocalDateTime;
import lombok.*;

@Getter
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
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
