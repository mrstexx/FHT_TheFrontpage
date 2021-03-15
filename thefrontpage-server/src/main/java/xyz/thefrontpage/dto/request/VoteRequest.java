package xyz.thefrontpage.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.thefrontpage.entity.VoteType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteRequest {
    private VoteType voteType;
    private Long postId;
}
