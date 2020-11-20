package advolang.app.services;

import advolang.app.exceptions.RecommendationNotFound;
import advolang.app.exceptions.UserBadRequest;
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
	 * Method to get all recommendations that have been created
	 * @return A list of all recommendations
	 * @throws RecommendationNotFound 
	 */
	public List<Recommendation> getAllRecommendations() throws RecommendationNotFound;
	
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
     * Method to get an specific recommendation by the given id
     * @param id Id of the recommendation
     * @return The recommendation 
     * @throws RecommendationNotFound If the recommendation does not exist
     */
	public Recommendation getSpecificRecommendationWithUser(String username, String id) throws RecommendationNotFound;

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
     * @param userId
     */
    void addSubscription(String language, String userId) throws UserNotFound;
    
    /**
     * 
     * @param language
     * @param userId
     */
    void removeSubscription(String language, String userId) throws UserNotFound;

    /**
     * Get the average of scores related to a specific recommendation.
     * @param language  The language of that recommendation
     * @param recommendationId  Identifier of recommendation
     * @return  Numerical average of the scores
     */
    Double getScoreOfRecommendation(String language, String recommendationId)  throws RecommendationNotFound;

    Double rateRecommendation(String language, String recommendationId, Score newScore) throws RecommendationNotFound, UserNotFound, Exception;

	List<Recommendation> getFilterRecommendations(List<String> categories, String title, String difficulty, String type,String language, String user)  throws UserBadRequest,Exception;

}
