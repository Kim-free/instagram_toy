package com.example.instagram.post.entity;

import com.example.instagram.comment.entity.Comment;
import com.example.instagram.common.entity.BaseEntity;
import com.example.instagram.post.dto.PostCreateRequestDto;
import com.example.instagram.postimage.entity.PostImage;
import com.example.instagram.postlike.entity.PostLike;
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
public class Post extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Builder.Default
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<PostImage> images = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<PostLike> likes = new ArrayList<>();

    public void updateContent(String content) {
        this.content = content;
    }

    public static Post toEntity(PostCreateRequestDto requestDto, User author) {
        return Post.builder()
                .author(author)
                .content(requestDto.getContent())
                .build();
    }
}
