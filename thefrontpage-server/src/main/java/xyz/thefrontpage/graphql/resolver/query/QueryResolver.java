package xyz.thefrontpage.graphql.resolver.query;

import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.thefrontpage.entity.Comment;
import xyz.thefrontpage.entity.Community;
import xyz.thefrontpage.entity.Post;
import xyz.thefrontpage.service.CommentService;
import xyz.thefrontpage.service.CommunityService;
import xyz.thefrontpage.service.PostService;

import java.util.List;

@Component
@AllArgsConstructor
public class QueryResolver implements GraphQLQueryResolver {

    private final CommunityService communityService;
    private final PostService postService;
    private final CommentService commentService;

    public List<Community> getAllCommunities() {
        return communityService.getAllCommunities();
    }

    public Community getCommunityByName(String name) {
        return communityService.getCommunityByName(name);
    }

    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    public Post getPostById(Long id) {
        return postService.getPostById(id);
    }

    public List<Comment> getCommentsByPostId(Long postId) {
        return commentService.getCommentsByPostId(postId);
    }
}
