package com.example.instagram.comment.dto;

import com.example.instagram.comment.entity.Comment;
import com.example.instagram.post.entity.Post;
import com.example.instagram.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentCreateRequestDto {

    private Long authorId;
    private String content;

    @Builder
    private CommentCreateRequestDto(Long authorId, String content) {
        this.authorId = authorId;
        this.content = content;
    }

    public Comment toEntity(Post post, User author, Comment parent) {
        return Comment.builder()
                .post(post)
                .author(author)
                .parent(parent)
                .content(content)
                .build();
    }
}
