package xyz.thefrontpage.rest.resource;

import lombok.AllArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.thefrontpage.dto.CommentDto;
import xyz.thefrontpage.dto.PostDto;
import xyz.thefrontpage.dto.UserDto;
import xyz.thefrontpage.dto.request.PostRequest;
import xyz.thefrontpage.entity.Comment;
import xyz.thefrontpage.entity.Post;
import xyz.thefrontpage.mapper.CommentMapper;
import xyz.thefrontpage.mapper.PostMapper;
import xyz.thefrontpage.mapper.UserMapper;
import xyz.thefrontpage.service.CommentService;
import xyz.thefrontpage.service.PostService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostResource {

    private final PostService postService;
    private final CommentService commentService;

    @GetMapping("/")
    public ResponseEntity<List<PostDto>> getAllPosts() {
        List<PostDto> postsDto = postService.getAllPosts()
                .stream().map(PostMapper::mapToDto).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(postsDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Post post = postService.getPostById(id);
        EntityModel<PostDto> resource = EntityModel.of(PostMapper.mapToDto(post));
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getCommentsByPostId(id)).withRel("comments"));
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getPostAuthorByPostId(id)).withRel("author"));
        return ResponseEntity.status(HttpStatus.OK).body(resource);
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable Long postId) {
        List<Comment> allComments = commentService.getCommentsByPostId(postId);
        List<CommentDto> commentsDto = allComments.stream().map(CommentMapper::mapToDto).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(commentsDto);
    }

    @GetMapping("/{postId}/author")
    public ResponseEntity<UserDto> getPostAuthorByPostId(@PathVariable Long postId) {
        Post post = postService.getPostById(postId);
        return ResponseEntity.status(HttpStatus.OK).body(UserMapper.mapToDto(post.getUser()));
    }

    @PostMapping("/")
    public ResponseEntity<PostDto> createPost(@RequestBody PostRequest postRequest) {
        PostDto post = PostMapper.mapToDto(postService.createPost(postRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    @PutMapping("/")
    public ResponseEntity<?> updatePost(@RequestBody PostRequest postRequest) {
        postService.updatePost(postRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
