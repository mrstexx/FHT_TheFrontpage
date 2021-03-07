package xyz.thefrontpage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.thefrontpage.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
