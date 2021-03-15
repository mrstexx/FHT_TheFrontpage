package xyz.thefrontpage.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.thefrontpage.dto.request.CommentRequest;
import xyz.thefrontpage.entity.Comment;
import xyz.thefrontpage.entity.Post;
import xyz.thefrontpage.entity.User;
import xyz.thefrontpage.dto.CommentDto;
import xyz.thefrontpage.mapper.CommentMapper;
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
    public Comment createComment(CommentRequest commentRequest) {
        Post post = postRepository.findById(commentRequest.getPostId())
                .orElseThrow(() -> new IllegalStateException("No post found with id: " + commentRequest.getPostId()));
        Comment comment = CommentMapper.map(commentRequest, post, authService.getCurrentUser());
        commentRepository.save(comment);
        return comment;
    }

    @Transactional
    public void updateComment(CommentRequest commentRequest) {
        Comment comment = commentRepository.findById(commentRequest.getId())
                .orElseThrow(() -> new IllegalStateException("No comment found with id: " + commentRequest.getId()));
        comment.setBody(commentRequest.getBody());
        commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalStateException("No comment found with id: " + commentId));
        commentRepository.delete(comment);
    }

    @Transactional(readOnly = true)
    public List<Comment> getCommentsByPostId(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalStateException("No post found with id: " + postId));
        return commentRepository.findByPostId(post.getId());
    }
}
