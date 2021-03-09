package xyz.thefrontpage.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.thefrontpage.domain.Community;
import xyz.thefrontpage.domain.Post;
import xyz.thefrontpage.domain.User;
import xyz.thefrontpage.dto.PostInput;
import xyz.thefrontpage.dto.PostResponse;
import xyz.thefrontpage.repository.CommunityRepository;
import xyz.thefrontpage.repository.PostRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final AuthService authService;
    private final CommunityRepository communityRepository;

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PostResponse getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalStateException("No post found with id - " + id));
        return mapToDto(post);
    }

    @Transactional
    public PostResponse createPost(PostInput postInput) {
        User currentUser = authService.getCurrentUser();
        Community community = communityRepository.findByName(postInput.getCommunityName())
                .orElseThrow(() -> new IllegalStateException("Community with the name " + postInput.getCommunityName() + " could not be found."));
        Post newPost = mapPostOfRequest(postInput, currentUser, community);
        postRepository.save(newPost);
        return mapToDto(newPost);
    }

    private Post mapPostOfRequest(PostInput postInput, User currentUser, Community community) {
        return Post.builder()
                .title(postInput.getTitle())
                .body(postInput.getBody())
                .createdAt(LocalDateTime.now())
                .comments(Collections.emptyList())
                .community(community)
                .user(currentUser)
                .voteCount(0)
                .url(postInput.getUrl())
                .build();
    }

    private PostResponse mapToDto(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .body(post.getBody())
                .username(post.getUser().getUsername())
                .createdAt(post.getCreatedAt())
                .voteCount(post.getVoteCount())
                .commentCount(post.getComments().size())
                .communityName(post.getCommunity().getName())
                .url(post.getUrl())
                .build();
    }

}
