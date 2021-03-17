package xyz.thefrontpage.graphql.resolver.mutation;

import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.thefrontpage.dto.Auth;
import xyz.thefrontpage.dto.request.*;
import xyz.thefrontpage.entity.Comment;
import xyz.thefrontpage.entity.Community;
import xyz.thefrontpage.entity.Post;
import xyz.thefrontpage.service.*;

@Component
@AllArgsConstructor
public class MutationResolver implements GraphQLMutationResolver {

    private final AuthService authService;
    private final CommunityService communityService;
    private final PostService postService;
    private final CommentService commentService;
    private final VoteService voteService;

    /* AUTH MUTATIONS */
    public Auth login(LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    public boolean register(RegisterRequest registerRequest) {
        authService.register(registerRequest);
        return true;
    }

    public boolean confirmToken(String token) {
        authService.confirmToken(token);
        return true;
    }

    /* COMMUNITY MUTATIONS */
    public Community createCommunity(CommunityRequest communityRequest) {
        return communityService.createCommunity(communityRequest);
    }

    public boolean updateCommunity(CommunityRequest communityRequest) {
        communityService.updateCommunity(communityRequest);
        return true;
    }

    public boolean deleteCommunity(String name) {
        communityService.deleteCommunity(name);
        return true;
    }

    /* POST MUTATIONS */
    public Post createPost(PostRequest postRequest) {
        return postService.createPost(postRequest);
    }

    public boolean updatePost(PostRequest postRequest) {
        postService.updatePost(postRequest);
        return true;
    }

    public boolean deletePost(Long postId) {
        postService.deletePost(postId);
        return true;
    }

    /* COMMENT MUTATIONS */
    public Comment createComment(CommentRequest commentRequest) {
        return commentService.createComment(commentRequest);
    }

    public boolean updateComment(CommentRequest commentRequest) {
        commentService.updateComment(commentRequest);
        return true;
    }

    public boolean deleteComment(Long commentId) {
        commentService.deleteComment(commentId);
        return true;
    }

    /* VOTE MUTATIONS */
    public boolean vote(VoteRequest voteRequest) {
        voteService.vote(voteRequest);
        return true;
    }
}
