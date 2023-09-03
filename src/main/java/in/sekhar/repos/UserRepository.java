package in.sekhar.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.sekhar.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);

	Optional<User> findByUsernameOrEmail(String username, String email);

	Optional<User> findByUsername(String username);

	boolean existsByUsername(String username);

	boolean existsByEmail(String email);
}
