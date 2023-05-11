package in.swathi.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.swathi.entity.Comments;

public interface CommentRepository extends JpaRepository<Comments, Integer>{

}
