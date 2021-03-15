package xyz.thefrontpage.graphql.resolver;

import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.thefrontpage.entity.Post;
import xyz.thefrontpage.repository.PostRepository;

import java.util.List;

@Component
@AllArgsConstructor
public class PostResolver implements GraphQLQueryResolver {

    private final PostRepository postRepository;

    public List<Post> allPosts() {
        return postRepository.findAll();
    }

}
