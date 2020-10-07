package advolang.app.repository;

import advolang.app.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
 
	/**
	 * 
	 * @param username
	 * @return
	 */
	Optional<User> findByUsername(String username);

	/**
	 * 
	 * @param username
	 * @return
	 */
    Boolean existsByUsername(String username);
}
