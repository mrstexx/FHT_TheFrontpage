package xyz.thefrontpage.rest.resource;

import lombok.AllArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.thefrontpage.dto.PostDto;
import xyz.thefrontpage.dto.UserDto;
import xyz.thefrontpage.dto.request.CommunityRequest;
import xyz.thefrontpage.dto.CommunityDto;
import xyz.thefrontpage.entity.Community;
import xyz.thefrontpage.mapper.CommunityMapper;
import xyz.thefrontpage.mapper.PostMapper;
import xyz.thefrontpage.mapper.UserMapper;
import xyz.thefrontpage.service.CommunityService;
import xyz.thefrontpage.service.PostService;
import xyz.thefrontpage.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/community")
@AllArgsConstructor
public class CommunityResource {

    private final CommunityService communityService;
    private final PostService postService;
    private final UserService userService;

    @GetMapping("/")
    public ResponseEntity<List<CommunityDto>> getAllCommunities() {
        List<CommunityDto> communitiesDto = communityService.getAllCommunities()
                .stream().map(CommunityMapper::mapToDto).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(communitiesDto);
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getByName(@PathVariable String name) {
        EntityModel<CommunityDto> resource = EntityModel.of(CommunityMapper.mapToDto(communityService.getCommunityByName(name)));
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getPostsByCommunityName(name)).withRel("posts"));
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getCommunityUsersByCommunityName(name)).withRel("users"));
        return ResponseEntity.status(HttpStatus.OK).body(resource);
    }

    @GetMapping("/{name}/posts")
    public ResponseEntity<?> getPostsByCommunityName(@PathVariable String name) {
        List<PostDto> allPosts = postService.getAllByCommunityName(name)
                .stream().map(PostMapper::mapToDto).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(allPosts);
    }

    @GetMapping("/{name}/users")
    public ResponseEntity<?> getCommunityUsersByCommunityName(@PathVariable String name) {
        List<UserDto> allUsers = userService.getAllByCommunityName(name)
                .stream().map(UserMapper::mapToDto).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(allUsers);
    }

    @PostMapping("/")
    public ResponseEntity<CommunityDto> createCommunity(@RequestBody CommunityRequest communityRequest) {
        Community community = communityService.createCommunity(communityRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(CommunityMapper.mapToDto(community));
    }

    @PutMapping("/")
    public ResponseEntity<?> updateCommunity(@RequestBody CommunityRequest communityRequest) {
        communityService.updateCommunity(communityRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<?> deleteCommunity(@PathVariable String name) {
        communityService.deleteCommunity(name);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/{communityName}/follow")
    public ResponseEntity<?> followCommunity(@PathVariable String communityName) {
        communityService.followCommunity(communityName);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/{communityName}/unfollow")
    public ResponseEntity<?> unfollowCommunity(@PathVariable String communityName) {
        communityService.unfollowCommunity(communityName);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
