package xyz.thefrontpage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.thefrontpage.entity.Community;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Long> {
    @Override
    Optional<Community> findById(Long id);

    Optional<Community> findByName(String communityName);

    List<Community> findAllByMembers_Id(Long id);
}
