package advolang.app.services;

import advolang.app.exceptions.RecommendationNotFound;
import advolang.app.exceptions.UserNotFound;
import advolang.app.models.Recommendation;
import advolang.app.models.Score;

import java.util.List;

public interface RecommendationService {
	
	/**
	 * Method to add a new recommendation to the db
	 * @param recommendation The recommendation that is going to be saved
	 */
    void addRecommendation(Recommendation recommendation) throws RecommendationNotFound;
    
    /**
     * Method to get all recommendations created by an specific user
     * @return A list with all recommendations
     * @throws UserNotFound If the user does not exist
     */
	List<Recommendation> getUserRecommendations(String creator) throws UserNotFound;

    /**
     * 
     * @param language
     * @return
     */
    List<Recommendation> getRecommendations(String language, List<String> values) throws RecommendationNotFound;

    /**
     * 
     * @param language
     * @return
     */
    List<Recommendation> getReportedRecommendations(String language) throws RecommendationNotFound;
    
    /**
     * 
     * @param language
     * @param id
     * @return
     * @throws RecommendationNotFound
     */
    Recommendation getSpecificRecommendation(String language, String id) throws RecommendationNotFound;

    /**
     * 
     * @param language
     * @return
     */
    List<String> getCategories(String language);
    
    /**
     * 
     * @param language
     * @param category
     */
    void addCategory(String language, String category);
    
    /**
     * 
     * @param language
     * @param userId
     */
    void addSubscription(String language, String userId);
    
    /**
     * 
     * @param language
     * @param userId
     */
    void removeSubscription(String language, String userId);

    /**
     * Get the average of scores related to a specific recommendation.
     * @param language  The language of that recommendation
     * @param recommendationId  Identifier of recommendation
     * @return  Numerical average of the scores
     */
    Double getScoreOfRecommendation(String language, String recommendationId)  throws RecommendationNotFound;

    /**
     *
     * @param language
     * @param recommendationId
     * @return
     */
    Double rateRecommendation(String language, String recommendationId, Score newScore) throws RecommendationNotFound, UserNotFound, Exception;
}
