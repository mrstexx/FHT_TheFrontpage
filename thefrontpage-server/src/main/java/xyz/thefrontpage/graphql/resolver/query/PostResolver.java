package xyz.thefrontpage.graphql.resolver.query;

import graphql.kickstart.tools.GraphQLResolver;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.thefrontpage.entity.Comment;
import xyz.thefrontpage.entity.Community;
import xyz.thefrontpage.entity.Post;
import xyz.thefrontpage.entity.User;
import xyz.thefrontpage.service.CommentService;
import xyz.thefrontpage.service.CommunityService;
import xyz.thefrontpage.service.UserService;

import java.util.List;

@Component
@AllArgsConstructor
public class PostResolver implements GraphQLResolver<Post> {

    private final UserService userService;
    private final CommunityService communityService;
    private final CommentService commentService;

    public Community community(Post post) {
        return communityService.getCommunityById(post.getCommunity().getId());
    }

    public List<Comment> comments(Post post) {
        return commentService.getCommentsByPostId(post.getId());
    }

    public User user(Post post) {
        return userService.getUserById(post.getUser().getId());
    }

}
