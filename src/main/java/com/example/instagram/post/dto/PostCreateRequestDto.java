package com.example.instagram.post.dto;

import com.example.instagram.post.entity.Post;
import com.example.instagram.user.entity.User;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCreateRequestDto {

    private Long authorId;
    private String content;
    private List<PostImageRequestDto> images;

    @Builder
    private PostCreateRequestDto(Long authorId, String content, List<PostImageRequestDto> images) {
        this.authorId = authorId;
        this.content = content;
        this.images = images;
    }

    public Post toEntity(User author) {
        return Post.builder()
                .author(author)
                .content(content)
                .build();
    }
}
