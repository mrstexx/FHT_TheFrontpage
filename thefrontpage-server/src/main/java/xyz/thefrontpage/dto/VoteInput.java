package xyz.thefrontpage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.thefrontpage.domain.VoteType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteInput {
    private VoteType voteType;
    private Long postId;
}
