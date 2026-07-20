package com.example.instagram.comment.service;

import com.example.instagram.comment.dto.CommentCreateRequestDto;
import com.example.instagram.comment.dto.CommentResponseDto;
import com.example.instagram.comment.dto.CommentUpdateRequestDto;
import com.example.instagram.comment.dto.ReplyResponseDto;
import com.example.instagram.comment.entity.Comment;
import com.example.instagram.comment.repository.CommentRepository;
import com.example.instagram.commentlike.repository.CommentLikeRepository;
import com.example.instagram.post.entity.Post;
import com.example.instagram.post.repository.PostRepository;
import com.example.instagram.user.entity.User;
import com.example.instagram.user.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommentResponseDto create(Long postId, CommentCreateRequestDto requestDto) {
        Post post = getPost(postId);
        User author = getUser(requestDto.getAuthorId());
        Comment comment = commentRepository.save(requestDto.toEntity(post, author, null));
        return CommentResponseDto.toDto(
                comment,
                commentLikeRepository.countByCommentId(comment.getId()),
                commentRepository.countByParentId(comment.getId())
        );
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> getTopLevelComments(Long postId) {
        return commentRepository.findByPostIdAndParentIsNullOrderByCreatedAtAsc(postId)
                .stream()
                .map(comment -> CommentResponseDto.toDto(
                        comment,
                        commentLikeRepository.countByCommentId(comment.getId()),
                        commentRepository.countByParentId(comment.getId())
                ))
                .toList();
    }

    @Transactional
    public ReplyResponseDto createReply(Long commentId, CommentCreateRequestDto requestDto) {
        Comment parent = getComment(commentId);
        if (parent.getParent() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Replies cannot have replies");
        }

        User author = getUser(requestDto.getAuthorId());
        Comment reply = commentRepository.save(requestDto.toEntity(parent.getPost(), author, parent));
        return ReplyResponseDto.toDto(reply, commentLikeRepository.countByCommentId(reply.getId()));
    }

    @Transactional(readOnly = true)
    public List<ReplyResponseDto> getReplies(Long commentId) {
        return commentRepository.findByParentIdOrderByCreatedAtAsc(commentId)
                .stream()
                .map(comment -> ReplyResponseDto.toDto(
                        comment,
                        commentLikeRepository.countByCommentId(comment.getId())
                ))
                .toList();
    }

    @Transactional
    public CommentResponseDto update(Long commentId, CommentUpdateRequestDto requestDto) {
        Comment comment = getComment(commentId);
        validateAuthor(comment, requestDto.getUserId());
        comment.updateContent(requestDto.getContent());
        return CommentResponseDto.toDto(
                comment,
                commentLikeRepository.countByCommentId(comment.getId()),
                commentRepository.countByParentId(comment.getId())
        );
    }

    @Transactional
    public void delete(Long commentId, Long userId) {
        Comment comment = getComment(commentId);
        validateAuthor(comment, userId);
        commentRepository.delete(comment);
    }

    private Post getPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    private Comment getComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found"));
    }

    private void validateAuthor(Comment comment, Long userId) {
        if (!comment.getAuthor().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only the author can change this comment");
        }
    }

}
