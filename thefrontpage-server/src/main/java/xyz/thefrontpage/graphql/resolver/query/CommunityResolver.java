package xyz.thefrontpage.graphql.resolver.query;

import graphql.kickstart.tools.GraphQLResolver;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.thefrontpage.entity.Community;
import xyz.thefrontpage.entity.Post;
import xyz.thefrontpage.entity.User;
import xyz.thefrontpage.service.PostService;
import xyz.thefrontpage.service.UserService;

import java.util.List;

@Component
@AllArgsConstructor
public class CommunityResolver implements GraphQLResolver<Community> {

    private final UserService userService;
    private final PostService postService;

    public User user(Community community) {
        return userService.getUserById(community.getUser().getId());
    }

    public List<Post> posts(Community community) {
        return postService.getAllByCommunityId(community.getId());
    }

}
