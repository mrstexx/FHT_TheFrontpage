package xyz.thefrontpage.rest.resource;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.thefrontpage.dto.request.PostRequest;
import xyz.thefrontpage.dto.PostDto;
import xyz.thefrontpage.mapper.PostMapper;
import xyz.thefrontpage.service.PostService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostResource {

    private final PostService postService;

    @GetMapping("/")
    public ResponseEntity<List<PostDto>> getAllPosts() {
        List<PostDto> postsDto = postService.getAllPosts()
                .stream().map(PostMapper::mapToDto).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(postsDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(PostMapper.mapToDto(postService.getPostById(id)));
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
