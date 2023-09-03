package in.sekhar.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.sekhar.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByName(String name);
}
