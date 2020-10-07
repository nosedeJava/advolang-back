package advolang.app.services;

import advolang.app.exceptions.RecommendationNotFound;
import advolang.app.exceptions.UserNotFound;
import advolang.app.models.Recommendation;
import advolang.app.models.User;

import java.util.List;
import java.util.Map;

public interface RecommendationService {
	
	/**
	 * Method to add a new recommendation to the db
	 * @param recommendation The recommendation that is going to be saved
	 */
    public void addRecommendation(Recommendation recommendation);
    
    /**
     * Method to get all recommendations created by an specific user
     * @param user2 Creator of recommendations
     * @return A list with all recommendations
     * @throws UserNotFound If the user does not exist
     */
	public List<Recommendation> getUserRecommendations(User user) throws UserNotFound;

    /**
     * 
     * @param language
     * @param parameters
     * @return
     */
    public List<Recommendation> getRecommendations(String language, List<String> values);

    /**
     * 
     * @param language
     * @return
     */
    public List<Recommendation> getReportedRecommendations(String language);
    
    /**
     * 
     * @param language
     * @param id
     * @return
     * @throws RecommendationNotFound
     */
    public Recommendation getSpecificRecommendation(String language, long id) throws RecommendationNotFound;

    /**
     * 
     * @param language
     * @return
     */
    public List<String> getCategories(String language);
    
    /**
     * 
     * @param language
     * @param category
     */
    public void addCategory(String language, String category);
    
    /**
     * 
     * @param language
     * @param userId
     */
    public void addSubscription(String language, String userId);
    
    /**
     * 
     * @param language
     * @param userId
     */
    public void removeSubscription(String language, String userId);

}
