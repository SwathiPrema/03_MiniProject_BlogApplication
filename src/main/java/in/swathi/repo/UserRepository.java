package in.swathi.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.swathi.entity.UserDetails;

public interface UserRepository extends JpaRepository<UserDetails, Integer> {

	public UserDetails findByemail(String email);

	public UserDetails findByemailAndPassword(String email, String password);

	
}
