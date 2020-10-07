package advolang.app.repository;

import advolang.app.models.ERole;
import advolang.app.models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
	
	/**
	 * 
	 * @param name
	 * @return
	 */
    Optional<Role> findByName(ERole name);
}
