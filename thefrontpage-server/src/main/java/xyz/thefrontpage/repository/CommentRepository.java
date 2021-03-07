package xyz.thefrontpage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.thefrontpage.domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
