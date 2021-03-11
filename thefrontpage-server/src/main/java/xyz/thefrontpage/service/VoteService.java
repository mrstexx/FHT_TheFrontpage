package xyz.thefrontpage.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.thefrontpage.domain.Post;
import xyz.thefrontpage.domain.User;
import xyz.thefrontpage.domain.Vote;
import xyz.thefrontpage.domain.VoteType;
import xyz.thefrontpage.dto.VoteInput;
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
    public void vote(VoteInput voteInput) {
        User currentUser = authService.getCurrentUser();
        Post post = postRepository.findById(voteInput.getPostId())
                .orElseThrow(() -> new IllegalStateException("Post not found with ID: " + voteInput.getPostId()));
        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByIdDesc(post, currentUser);
        if (voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteInput.getVoteType())) {
            throw new IllegalStateException("You have already " + voteInput.getVoteType() + "d for this post");
        }
        if (VoteType.UP_VOTE.equals(voteInput.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }
        Vote newVote = this.mapToVote(voteInput, currentUser, post);
        voteRepository.save(newVote);
        postRepository.save(post);
    }

    private Vote mapToVote(VoteInput voteInput, User currentUser, Post post) {
        return Vote.builder()
                .post(post)
                .user(currentUser)
                .voteType(voteInput.getVoteType())
                .build();
    }

}
