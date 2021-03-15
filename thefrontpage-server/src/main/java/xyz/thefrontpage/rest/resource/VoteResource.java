package xyz.thefrontpage.rest.resource;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.thefrontpage.dto.request.VoteRequest;
import xyz.thefrontpage.service.VoteService;

@RestController
@RequestMapping("/api/votes/")
@AllArgsConstructor
public class VoteResource {

    private final VoteService voteService;

    @PostMapping("/")
    public ResponseEntity<?> vote(@RequestBody VoteRequest voteRequest) {
        voteService.vote(voteRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
