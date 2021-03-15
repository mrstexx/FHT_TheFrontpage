package xyz.thefrontpage.rest.resource;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.thefrontpage.dto.CommentDto;
import xyz.thefrontpage.dto.request.CommentRequest;
import xyz.thefrontpage.entity.Comment;
import xyz.thefrontpage.mapper.CommentMapper;
import xyz.thefrontpage.service.CommentService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comments/")
@AllArgsConstructor
public class CommentResource {

    private final CommentService commentService;

    @PostMapping("/")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentRequest commentRequest) {
        Comment newComment = commentService.createComment(commentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(CommentMapper.mapToDto(newComment));
    }

    @PutMapping
    public ResponseEntity<?> updateComment(@RequestBody CommentRequest commentRequest) {
        commentService.updateComment(commentRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable Long postId) {
        List<Comment> allComments = commentService.getCommentsByPostId(postId);
        List<CommentDto> commentsDto = allComments.stream().map(CommentMapper::mapToDto).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(commentsDto);
    }
}
