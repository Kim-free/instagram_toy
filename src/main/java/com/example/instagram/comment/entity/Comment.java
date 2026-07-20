package com.example.instagram.comment.entity;

import com.example.instagram.comment.dto.CommentCreateRequestDto;
import com.example.instagram.commentlike.entity.CommentLike;
import com.example.instagram.common.entity.BaseEntity;
import com.example.instagram.post.entity.Post;
import com.example.instagram.user.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Getter
@Entity
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Comment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Builder.Default
    @OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comment> children = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<CommentLike> likes = new ArrayList<>();

    public void updateContent(String content) {
        this.content = content;
    }

    public static Comment toEntity(CommentCreateRequestDto requestDto, Post post, User author, Comment parent) {
        return Comment.builder()
                .post(post)
                .author(author)
                .parent(parent)
                .content(requestDto.getContent())
                .build();
    }
}
