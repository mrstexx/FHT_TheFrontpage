package xyz.thefrontpage.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.thefrontpage.repository.CommunityRepository;

@Service
@AllArgsConstructor
public class CommunityService {

    private final CommunityRepository communityRepository;

}
