package xyz.thefrontpage.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.thefrontpage.repository.VoteRepository;

@Service
@AllArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;

}
