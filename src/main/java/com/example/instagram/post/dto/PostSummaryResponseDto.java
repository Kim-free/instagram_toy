package com.example.instagram.post.dto;

import com.example.instagram.common.util.ElapsedTimeFormatter;
import com.example.instagram.post.entity.Post;
import java.time.LocalDateTime;
import java.util.List;
import lombok.*;

@Getter
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
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
