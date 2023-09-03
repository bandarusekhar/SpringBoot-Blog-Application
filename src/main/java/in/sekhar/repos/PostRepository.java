package in.sekhar.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.sekhar.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

	List<Post> findByCategoryId(Long categoryId);
}
