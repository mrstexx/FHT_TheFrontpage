package xyz.thefrontpage.rest.resource;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.thefrontpage.dto.CommentDto;
import xyz.thefrontpage.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/api/comments/")
@AllArgsConstructor
public class CommentResource {

    private final CommentService commentService;

    @PostMapping("/")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto) {
        CommentDto newComment = commentService.createComment(commentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newComment);
    }

    @PutMapping
    public ResponseEntity<?> updateComment(@RequestBody CommentDto commentDto) {
        commentService.updateComment(commentDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable Long postId) {
        List<CommentDto> allComments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.status(HttpStatus.OK).body(allComments);
    }

}
