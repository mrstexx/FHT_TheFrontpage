package xyz.thefrontpage.mapper;

import xyz.thefrontpage.dto.PostDto;
import xyz.thefrontpage.dto.request.PostRequest;
import xyz.thefrontpage.entity.Community;
import xyz.thefrontpage.entity.Post;
import xyz.thefrontpage.entity.User;

import java.time.LocalDateTime;
import java.util.Collections;

public class PostMapper {

    public static PostDto mapToDto(Post post) {
        return PostDto.builder()
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

    public static Post map(PostRequest postRequest, User currentUser, Community community) {
        return Post.builder()
                .title(postRequest.getTitle())
                .body(postRequest.getBody())
                .createdAt(LocalDateTime.now())
                .comments(Collections.emptyList())
                .community(community)
                .user(currentUser)
                .voteCount(0)
                .url(postRequest.getUrl())
                .build();
    }
}
