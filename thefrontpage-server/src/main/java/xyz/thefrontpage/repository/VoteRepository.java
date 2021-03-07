package xyz.thefrontpage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.thefrontpage.domain.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
}
