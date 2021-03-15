package xyz.thefrontpage.mapper;

import xyz.thefrontpage.dto.CommunityDto;
import xyz.thefrontpage.dto.request.CommunityRequest;
import xyz.thefrontpage.entity.Community;
import xyz.thefrontpage.entity.User;

import java.time.LocalDateTime;
import java.util.Collections;

public class CommunityMapper {
    public static CommunityDto mapToDto(Community community) {
        return CommunityDto.builder()
                .id(community.getId())
                .name(community.getName())
                .description(community.getDescription())
                .username(community.getUser().getUsername())
                .build();
    }

    public static Community map(CommunityRequest communityRequest, User user) {
        return Community.builder()
                .name(communityRequest.getName())
                .description(communityRequest.getDescription())
                .user(user)
                .createdAt(LocalDateTime.now())
                .posts(Collections.emptyList())
                .build();
    }
}
