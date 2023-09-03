package in.sekhar.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import in.sekhar.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
