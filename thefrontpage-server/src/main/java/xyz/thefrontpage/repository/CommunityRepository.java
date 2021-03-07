package xyz.thefrontpage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.thefrontpage.domain.Community;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Long> {
}
