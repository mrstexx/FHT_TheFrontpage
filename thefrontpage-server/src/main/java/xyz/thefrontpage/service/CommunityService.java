package xyz.thefrontpage.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.thefrontpage.dto.request.CommunityRequest;
import xyz.thefrontpage.entity.Community;
import xyz.thefrontpage.entity.User;
import xyz.thefrontpage.mapper.CommunityMapper;
import xyz.thefrontpage.repository.CommunityRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
@AllArgsConstructor
public class CommunityService {

    private final Lock lock = new ReentrantLock();

    private final CommunityRepository communityRepository;
    private final AuthService authService;

    @Transactional(readOnly = true)
    public List<Community> getAllCommunities() {
        return communityRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Community getCommunityByName(String communityName) {
        return communityRepository.findByName(communityName)
                .orElseThrow(() -> new IllegalStateException("No community found with the name: " + communityName));
    }

    @Transactional(readOnly = true)
    public Community getCommunityById(Long id) {
        return communityRepository.findById(id).orElseThrow(() -> new IllegalStateException("Community could not be found"));
    }

    public List<Community> getAllCommunitiesByUserId(Long userId) {
        return communityRepository.findAllByMembers_Id(userId);
    }

    @Transactional
    public Community createCommunity(CommunityRequest communityRequest) {
        boolean existsCommunity = communityRepository.findByName(communityRequest.getName()).isPresent();
        if (existsCommunity) {
            throw new IllegalStateException("Community with the name '" + communityRequest.getName() + "' already exists");
        }
        User currentUser = authService.getCurrentUser();
        Community newCommunity = CommunityMapper.map(communityRequest, currentUser);
        communityRepository.save(newCommunity);
        return newCommunity;
    }

    @Transactional
    public void updateCommunity(CommunityRequest communityRequest) {
        String communityName = communityRequest.getName();
        Community community = communityRepository.findByName(communityName)
                .orElseThrow(() -> new IllegalStateException("No community found with the name: " + communityName));
        User currentUser = authService.getCurrentUser();
        if (!community.getCreatedBy().getUsername().equals(currentUser.getUsername())) {
            throw new IllegalStateException("Missing access rights to update community");
        }
        lock.lock();
        try {
            community.setDescription(communityRequest.getDescription());
            community.setName(communityRequest.getName());
        } finally {
            lock.unlock();
        }
        communityRepository.save(community);
    }

    @Transactional
    public void deleteCommunity(String communityName) {
        Community community = communityRepository.findByName(communityName)
                .orElseThrow(() -> new IllegalStateException("No community found with the name: " + communityName));
        User currentUser = authService.getCurrentUser();
        if (!community.getCreatedBy().getUsername().equals(currentUser.getUsername())) {
            throw new IllegalStateException("Missing access rights to delete community");
        }
        communityRepository.delete(community);
    }

    @Transactional
    public void followCommunity(String communityName) {
        Community community = this.getCommunityByName(communityName);
        User user = authService.getCurrentUser();
        lock.lock();
        try {
            community.getMembers().add(user);
            user.getCommunities().add(community);
        } finally {
            lock.unlock();
        }
        communityRepository.save(community);
    }

    @Transactional
    public void unfollowCommunity(String communityName) {
        Community community = this.getCommunityByName(communityName);
        User user = authService.getCurrentUser();
        lock.lock();
        try {
            community.getMembers().remove(user);
            user.getCommunities().remove(community);
        } finally {
            lock.unlock();
        }
        communityRepository.save(community);
    }
}
