package xyz.thefrontpage.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.thefrontpage.domain.Community;
import xyz.thefrontpage.domain.User;
import xyz.thefrontpage.dto.CommunityInput;
import xyz.thefrontpage.dto.CommunityResponse;
import xyz.thefrontpage.repository.CommunityRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommunityService {

    private final CommunityRepository communityRepository;
    private final AuthService authService;

    @Transactional(readOnly = true)
    public List<CommunityResponse> getAllCommunities() {
        return communityRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CommunityResponse getCommunityByName(String communityName) {
        Community community = communityRepository.findByName(communityName)
                .orElseThrow(() -> new IllegalStateException("No community found with the name: " + communityName));
        return mapToDto(community);
    }

    @Transactional
    public CommunityResponse createCommunity(CommunityInput communityInput) {
        User currentUser = authService.getCurrentUser();
        Community newCommunity = mapCommunityOfRequest(communityInput, currentUser);
        communityRepository.save(newCommunity);
        return mapToDto(newCommunity);
    }

    private CommunityResponse mapToDto(Community community) {
        return CommunityResponse.builder()
                .id(community.getId())
                .name(community.getName())
                .description(community.getDescription())
                .username(community.getUser().getUsername())
                .build();
    }

    private Community mapCommunityOfRequest(CommunityInput communityInput, User user) {
        return Community.builder()
                .name(communityInput.getName())
                .description(communityInput.getDescription())
                .user(user)
                .createdAt(LocalDateTime.now())
                .posts(Collections.emptyList())
                .build();
    }

}
