package xyz.thefrontpage.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.thefrontpage.entity.User;
import xyz.thefrontpage.repository.UserRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("Username could not be found"));
    }

    @Transactional(readOnly = true)
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User ID could not be found"));
    }

    @Transactional(readOnly = true)
    public List<User> getAllByCommunityId(Long communityId) {
        return userRepository.findAllByCommunities_Id(communityId);
    }

    @Transactional(readOnly = true)
    public List<User> getAllByCommunityName(String communityName) {
        return userRepository.findAllByCommunities_Name(communityName);
    }
}
