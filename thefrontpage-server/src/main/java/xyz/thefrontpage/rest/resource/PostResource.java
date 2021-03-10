package xyz.thefrontpage.rest.resource;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.thefrontpage.dto.PostInput;
import xyz.thefrontpage.dto.PostResponse;
import xyz.thefrontpage.service.PostService;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostResource {

    private final PostService postService;

    @GetMapping("/")
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPostById(id));
    }

    @PostMapping("/")
    public ResponseEntity<PostResponse> createPost(@RequestBody PostInput postInput) {
        PostResponse post = postService.createPost(postInput);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    @PutMapping("/")
    public ResponseEntity<?> updatePost(@RequestBody PostInput postInput) {
        postService.updatePost(postInput);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
