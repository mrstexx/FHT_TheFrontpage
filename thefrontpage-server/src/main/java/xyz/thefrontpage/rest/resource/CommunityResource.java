package xyz.thefrontpage.rest.resource;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.thefrontpage.dto.CommunityInput;
import xyz.thefrontpage.dto.CommunityResponse;
import xyz.thefrontpage.service.CommunityService;

import java.util.List;

@RestController
@RequestMapping("/api/community")
@AllArgsConstructor
public class CommunityResource {

    private final CommunityService communityService;

    @GetMapping("/")
    public ResponseEntity<List<CommunityResponse>> getAllPosts() {
        return ResponseEntity.status(HttpStatus.OK).body(communityService.getAllCommunities());
    }

    @GetMapping("/{name}")
    public ResponseEntity<CommunityResponse> getByName(@PathVariable String name) {
        return ResponseEntity.status(HttpStatus.OK).body(communityService.getCommunityByName(name));
    }

    @PostMapping("/")
    public ResponseEntity<CommunityResponse> createCommunity(@RequestBody CommunityInput communityInput) {
        CommunityResponse community = communityService.createCommunity(communityInput);
        return ResponseEntity.status(HttpStatus.CREATED).body(community);
    }

    @PutMapping("/")
    public ResponseEntity<?> updateCommunity(@RequestBody CommunityInput communityInput) {
        communityService.updateCommunity(communityInput);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<?> deleteCommunity(@PathVariable String name) {
        communityService.deleteCommunity(name);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
