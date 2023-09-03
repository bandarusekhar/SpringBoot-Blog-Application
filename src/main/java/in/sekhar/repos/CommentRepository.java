package in.sekhar.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.sekhar.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	
	List<Comment> findByPostId(Long PostId);

}
