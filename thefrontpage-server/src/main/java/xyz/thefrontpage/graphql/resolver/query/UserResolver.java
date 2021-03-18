package xyz.thefrontpage.graphql.resolver.query;

import graphql.kickstart.tools.GraphQLResolver;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.thefrontpage.entity.Community;
import xyz.thefrontpage.entity.User;
import xyz.thefrontpage.service.CommunityService;

import java.util.List;

@Component
@AllArgsConstructor
public class UserResolver implements GraphQLResolver<User> {

    private final CommunityService communityService;

    public List<Community> communities(User user) {
        return communityService.getAllCommunitiesByUserId(user.getId());
    }

}
