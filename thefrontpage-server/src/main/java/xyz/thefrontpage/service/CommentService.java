package xyz.thefrontpage.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.thefrontpage.domain.Comment;
import xyz.thefrontpage.domain.Post;
import xyz.thefrontpage.domain.User;
import xyz.thefrontpage.dto.CommentDto;
import xyz.thefrontpage.repository.CommentRepository;
import xyz.thefrontpage.repository.PostRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final AuthService authService;

    @Transactional
    public CommentDto createComment(CommentDto commentDto) {
        Post post = postRepository.findById(commentDto.getPostId())
                .orElseThrow(() -> new IllegalStateException("No post found with id: " + commentDto.getPostId()));
        Comment comment = mapComment(commentDto, post, authService.getCurrentUser());
        commentRepository.save(comment);
        return mapToDto(comment);
    }

    @Transactional
    public void updateComment(CommentDto commentDto) {
        Comment comment = commentRepository.findById(commentDto.getId())
                .orElseThrow(() -> new IllegalStateException("No comment found with id: " + commentDto.getId()));
        comment.setBody(commentDto.getBody());
        commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalStateException("No comment found with id: " + commentId));
        commentRepository.delete(comment);
    }

    @Transactional(readOnly = true)
    public List<CommentDto> getCommentsByPostId(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalStateException("No post found with id: " + postId));
        return commentRepository.findByPostId(post.getId()).stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private CommentDto mapToDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .body(comment.getBody())
                .createdAt(comment.getCreatedAt())
                .postId(comment.getPost().getId())
                .username(comment.getUser().getUsername())
                .build();
    }

    private Comment mapComment(CommentDto commentDto, Post post, User user) {
        return Comment.builder()
                .id(commentDto.getId())
                .post(post)
                .body(commentDto.getBody())
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
