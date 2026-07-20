package com.example.instagram.commentlike.service;

import com.example.instagram.comment.entity.Comment;
import com.example.instagram.comment.repository.CommentRepository;
import com.example.instagram.commentlike.dto.CommentLikeCountResponseDto;
import com.example.instagram.commentlike.dto.CommentLikeRequestDto;
import com.example.instagram.commentlike.dto.CommentLikeResponseDto;
import com.example.instagram.commentlike.entity.CommentLike;
import com.example.instagram.commentlike.repository.CommentLikeRepository;
import com.example.instagram.user.entity.User;
import com.example.instagram.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommentLikeResponseDto toggle(Long commentId, CommentLikeRequestDto requestDto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found"));
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        boolean liked = commentLikeRepository.findByUserIdAndCommentId(requestDto.getUserId(), commentId)
                .map(commentLike -> {
                    commentLikeRepository.delete(commentLike);
                    return false;
                })
                .orElseGet(() -> {
                    commentLikeRepository.save(CommentLike.toEntity(requestDto, user, comment));
                    return true;
                });

        return CommentLikeResponseDto.toDto(commentId, liked, commentLikeRepository.countByCommentId(commentId));
    }

    @Transactional(readOnly = true)
    public CommentLikeCountResponseDto count(Long commentId) {
        if (!commentRepository.existsById(commentId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found");
        }

        return CommentLikeCountResponseDto.toDto(commentId, commentLikeRepository.countByCommentId(commentId));
    }
}
