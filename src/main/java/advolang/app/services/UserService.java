package advolang.app.services;

import advolang.app.exceptions.RecommendationNotFound;
import advolang.app.exceptions.UserNotFound;
import advolang.app.models.Recommendation;
import advolang.app.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    
	/**
	 * Method to add a new user
	 * @param user The new user that is going to be save on db
	 */
	public void saveUser(User user);
	
	/**
	 * Method to get an user by the given id
	 * @param userId The Id of the user
	 * @return The user if exists
	 * @throws UserNotFound If the user does not exist
	 */
	public User getUserById(String userId) throws UserNotFound;
	
	/**
	 * Method to get an user by the given username
	 * @param username The Username of the user
	 * @return The user if exists
	 * @throws UserNotFound If the user does not exist
	 */
	User getUserByUsername(String username) throws UserNotFound;	

	/**
	 * Method to get all recommendations that a specific user have been save
	 * @param id Id of the user
	 * @return A list with all recommendations
	 * @throws UserNotFound If the user does not exist
	 */
	public List<Recommendation> getSavedRecommendations(String id) throws UserNotFound;
	
	/**
	 * Method to save a recommendation to an user
	 * @param userId Id of the user
	 * @param recommendationId Id of the recommendation
	 */
    public void saveRecommendation(String userId, long recommendationId);

    /**
     * Method to delete a recommendation for an user
     * @param userId Id of the user
     * @param recommendationId Id of the recommendation
     * @throws UserNotFound If the user does not exist
     * @throws RecommendationNotFound If the recommendation does noy exist
     */
    public void removeSavedRecommendation(String userId, long recommendationId) throws UserNotFound, RecommendationNotFound;


}

