package xyz.thefrontpage.rest.resource;

import lombok.AllArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.thefrontpage.dto.CommentDto;
import xyz.thefrontpage.dto.request.CommentRequest;
import xyz.thefrontpage.entity.Comment;
import xyz.thefrontpage.mapper.CommentMapper;
import xyz.thefrontpage.mapper.UserMapper;
import xyz.thefrontpage.service.CommentService;

@RestController
@RequestMapping("/api/comments/")
@AllArgsConstructor
public class CommentResource {

    private final CommentService commentService;

    @GetMapping("/{commentId}")
    public ResponseEntity<?> getComment(@PathVariable Long commentId) {
        Comment comment = commentService.getCommentById(commentId);
        EntityModel<CommentDto> resource = EntityModel.of(CommentMapper.mapToDto(comment));
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getCommentAuthor(commentId)).withRel("author"));
        return ResponseEntity.status(HttpStatus.OK).body(resource);
    }

    @GetMapping("/{commentId}/author")
    public ResponseEntity<?> getCommentAuthor(@PathVariable Long commentId) {
        Comment comment = commentService.getCommentById(commentId);
        return ResponseEntity.status(HttpStatus.OK).body(UserMapper.mapToDto(comment.getUser()));
    }

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
}
