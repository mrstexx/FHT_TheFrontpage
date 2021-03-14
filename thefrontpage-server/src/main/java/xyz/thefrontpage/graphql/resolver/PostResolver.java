package xyz.thefrontpage.graphql.resolver;

import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.thefrontpage.domain.Post;
import xyz.thefrontpage.dto.PostResponse;
import xyz.thefrontpage.service.PostService;

import java.util.List;

@Component
@AllArgsConstructor
public class PostResolver implements GraphQLQueryResolver {

    private final PostService postService;

    public List<Post> allPosts() {
        return postService.getPosts();
    }

}
