package advolang.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import advolang.app.models.Recommendation;
import advolang.app.models.User;

@Repository
public interface RecomRepository extends MongoRepository<Recommendation, String> {
	
	/**
	 * Method to get a specific recommendation
	 * 
	 * @param id Id of the recommendation that wants to find
	 * @return The recommendation if exists
	 */
	public Optional<Recommendation> findById (String id);
	
	/**
	 * Method to get all recommendations of an specific user
	 * 
	 * @param creator User 
	 * @return A list with all recommendations of the user
	 */
	public List<Recommendation> findByCreator(User creator);
	
	/**
	 * Method to delete a recommendation
	 * 
	 * @param recom The recommendation that is going to be deleted
	 */
	public void delete (Recommendation recom);

}
