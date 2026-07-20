package com.example.instagram.post.dto;

import com.example.instagram.common.util.ElapsedTimeFormatter;
import com.example.instagram.post.entity.Post;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostSummaryResponseDto {

    private Long postId;
    private Long authorId;
    private String nickname;
    private String content;
    private List<PostImageResponseDto> images;
    private long likeCount;
    private long commentCount;
    private LocalDateTime createdAt;
    private String elapsedTime;

    @Builder
    private PostSummaryResponseDto(Long postId, Long authorId, String nickname, String content,
                                   List<PostImageResponseDto> images, long likeCount, long commentCount,
                                   LocalDateTime createdAt, String elapsedTime) {
        this.postId = postId;
        this.authorId = authorId;
        this.nickname = nickname;
        this.content = content;
        this.images = images;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.createdAt = createdAt;
        this.elapsedTime = elapsedTime;
    }

    public static PostSummaryResponseDto toDto(Post post, List<PostImageResponseDto> images,
                                               long likeCount, long commentCount) {
        return PostSummaryResponseDto.builder()
                .postId(post.getId())
                .authorId(post.getAuthor().getId())
                .nickname(post.getAuthor().getNickname())
                .content(post.getContent())
                .images(images)
                .likeCount(likeCount)
                .commentCount(commentCount)
                .createdAt(post.getCreatedAt())
                .elapsedTime(ElapsedTimeFormatter.format(post.getCreatedAt()))
                .build();
    }
}
