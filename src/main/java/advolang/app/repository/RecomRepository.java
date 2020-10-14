package advolang.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import advolang.app.models.Recommendation;

@Repository
public interface RecomRepository extends MongoRepository<Recommendation, String> {
	
	/**
	 * Method to get a specific recommendation
	 * 
	 * @param id Id of the recommendation that wants to find
	 * @return The recommendation if exists
	 */
	Optional<Recommendation> findById (String id);
	
	/**
	 * Method to get all recommendations of an specific user
	 * @return A list with all recommendations of the user
	 */
	@Query(value = "{'creator' : ?0 }")
	List<Recommendation> findByCreator(String creator);

	/**
	 * 
	 */
	List<Recommendation> findAll ();
	
	/**
	 * 
	 * @param language
	 * @return A list with all recommendations from a language
	 */
	List<Recommendation> findByLanguage(String language);
	/**
	 * 
	 * @return A list with all reported recommendations from a language 
	 */
	List<Recommendation> findByReportedIsTrueAndLanguage(String language);
	/**
	 * Method to delete a recommendation
	 * 
	 * @param recom The recommendation that is going to be deleted
	 */
	void delete (Recommendation recom);

}
