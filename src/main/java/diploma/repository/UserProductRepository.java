package diploma.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import diploma.entity.UserProduct;
@Repository
public interface UserProductRepository extends JpaRepository<UserProduct, Long>{

	List<UserProduct> findByUserId(long id);
	
}
