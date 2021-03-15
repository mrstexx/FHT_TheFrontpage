package xyz.thefrontpage.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.thefrontpage.entity.Post;
import xyz.thefrontpage.entity.User;
import xyz.thefrontpage.entity.Vote;
import xyz.thefrontpage.entity.VoteType;
import xyz.thefrontpage.dto.request.VoteRequest;
import xyz.thefrontpage.repository.PostRepository;
import xyz.thefrontpage.repository.VoteRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class VoteService {

    private final PostRepository postRepository;
    private final VoteRepository voteRepository;
    private final AuthService authService;

    @Transactional
    public void vote(VoteRequest voteRequest) {
        User currentUser = authService.getCurrentUser();
        Post post = postRepository.findById(voteRequest.getPostId())
                .orElseThrow(() -> new IllegalStateException("Post not found with ID: " + voteRequest.getPostId()));
        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByIdDesc(post, currentUser);
        if (voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteRequest.getVoteType())) {
            throw new IllegalStateException("You have already " + voteRequest.getVoteType() + "d for this post");
        }
        if (VoteType.UP_VOTE.equals(voteRequest.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }
        Vote newVote = this.mapToVote(voteRequest, currentUser, post);
        voteRepository.save(newVote);
        postRepository.save(post);
    }

    private Vote mapToVote(VoteRequest voteRequest, User currentUser, Post post) {
        return Vote.builder()
                .post(post)
                .user(currentUser)
                .voteType(voteRequest.getVoteType())
                .build();
    }

}
