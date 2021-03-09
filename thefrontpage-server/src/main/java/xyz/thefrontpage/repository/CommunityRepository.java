package xyz.thefrontpage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.thefrontpage.domain.Community;

import java.util.Optional;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Long> {
    @Override
    Optional<Community> findById(Long aLong);

    Optional<Community> findByName(String communityName);
}
