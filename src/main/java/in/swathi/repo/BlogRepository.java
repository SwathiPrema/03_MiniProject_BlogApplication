package in.swathi.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.swathi.entity.Blogs;

public interface BlogRepository extends JpaRepository<Blogs, Integer>{

	List<Blogs> findByTitleContaining(String title);
}
