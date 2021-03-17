package xyz.thefrontpage.rest.resource;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.thefrontpage.dto.request.CommunityRequest;
import xyz.thefrontpage.dto.CommunityDto;
import xyz.thefrontpage.entity.Community;
import xyz.thefrontpage.mapper.CommunityMapper;
import xyz.thefrontpage.service.CommunityService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/community")
@AllArgsConstructor
public class CommunityResource {

    private final CommunityService communityService;

    @GetMapping("/")
    public ResponseEntity<List<CommunityDto>> getAllCommunities() {
        List<CommunityDto> communitiesDto = communityService.getAllCommunities()
                .stream().map(CommunityMapper::mapToDto).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(communitiesDto);
    }

    @GetMapping("/{name}")
    public ResponseEntity<CommunityDto> getByName(@PathVariable String name) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommunityMapper.mapToDto(communityService.getCommunityByName(name)));
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

}
