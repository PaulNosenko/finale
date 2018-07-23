package diploma.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import diploma.entity.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	User findByEmail(String email);
	
	//Page<User> findAll(int page, int count);
	
}
