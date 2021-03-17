package xyz.thefrontpage.graphql.resolver;

import graphql.kickstart.tools.GraphQLResolver;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.thefrontpage.entity.Comment;
import xyz.thefrontpage.entity.Post;
import xyz.thefrontpage.entity.User;
import xyz.thefrontpage.service.PostService;
import xyz.thefrontpage.service.UserService;

@Component
@AllArgsConstructor
public class CommentResolver implements GraphQLResolver<Comment> {

    private final UserService userService;
    private final PostService postService;

    public User user(Comment comment) {
        return userService.getUserById(comment.getUser().getId());
    }

    public Post post(Comment comment) {
        return postService.getPostById(comment.getPost().getId());
    }
}
