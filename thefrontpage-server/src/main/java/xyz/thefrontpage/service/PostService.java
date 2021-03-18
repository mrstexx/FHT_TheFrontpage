package xyz.thefrontpage.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.thefrontpage.dto.request.PostRequest;
import xyz.thefrontpage.entity.Community;
import xyz.thefrontpage.entity.Post;
import xyz.thefrontpage.entity.User;
import xyz.thefrontpage.dto.PostDto;
import xyz.thefrontpage.mapper.PostMapper;
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
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Post getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("No post found with id - " + id));
        return post;
    }

    @Transactional(readOnly = true)
    public List<Post> getAllByCommunityId(Long communityId) {
        return postRepository.findAllByCommunityId(communityId);
    }

    @Transactional(readOnly = true)
    public List<Post> getAllByCommunityName(String communityName) {
        return postRepository.findAllByCommunityName(communityName);
    }

    @Transactional
    public Post createPost(PostRequest postRequest) {
        User currentUser = authService.getCurrentUser();
        Community community = communityRepository.findByName(postRequest.getCommunityName())
                .orElseThrow(() -> new IllegalStateException("Community with the name " +
                        postRequest.getCommunityName() + " could not be found."));
        Post newPost = PostMapper.map(postRequest, currentUser, community);
        postRepository.save(newPost);
        return newPost;
    }

    @Transactional
    public void updatePost(PostRequest postRequest) {
        User currentUser = authService.getCurrentUser();
        Post post = postRepository.findById(postRequest.getId())
                .orElseThrow(() -> new IllegalStateException("No post found with id - " + postRequest.getId()));
        if (!post.getUser().getUsername().equals(currentUser.getUsername())) {
            throw new IllegalStateException("Missing access rights to update post");
        }
        post.setTitle(postRequest.getTitle());
        post.setBody(postRequest.getBody());
        post.setUrl(postRequest.getUrl());
        postRepository.save(post);
    }

    @Transactional
    public void deletePost(Long id) {
        User currentUser = authService.getCurrentUser();
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("No post found with id - " + id));
        if (!post.getUser().getUsername().equals(currentUser.getUsername())) {
            throw new IllegalStateException("Missing access rights to delete post");
        }
        postRepository.delete(post);
    }
}
