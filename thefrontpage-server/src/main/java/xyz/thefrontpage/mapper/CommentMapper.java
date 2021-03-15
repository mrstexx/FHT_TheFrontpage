package xyz.thefrontpage.mapper;

import xyz.thefrontpage.dto.CommentDto;
import xyz.thefrontpage.dto.request.CommentRequest;
import xyz.thefrontpage.entity.Comment;
import xyz.thefrontpage.entity.Post;
import xyz.thefrontpage.entity.User;

import java.time.LocalDateTime;

public class CommentMapper {
    public static CommentDto mapToDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .body(comment.getBody())
                .createdAt(comment.getCreatedAt())
                .postId(comment.getPost().getId())
                .username(comment.getUser().getUsername())
                .build();
    }

    public static Comment map(CommentRequest commentRequest, Post post, User user) {
        return Comment.builder()
                .id(commentRequest.getId())
                .post(post)
                .body(commentRequest.getBody())
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
