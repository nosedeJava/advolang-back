package advolang.app.services;

import java.util.List;

import org.springframework.stereotype.Service;

import advolang.app.exceptions.RecommendationNotFound;
import advolang.app.exceptions.UserBadRequest;
import advolang.app.exceptions.UserNotFound;
import advolang.app.models.Recommendation;
import advolang.app.models.User;

/**
 * UserService
 */
@Service
public class UserService {

    public void createAccount(User user) throws UserBadRequest{
	}

	public User getUser(String id) throws UserNotFound{
		return null;
	}

	public List<Recommendation> getSavedRecommendations(String id) throws UserNotFound{
		return null;
	}

	public void saveRecommendation(String userId, long recommendationId) {
	}

	public void removeSavedRecommendation(String userId, long recommendationId) throws UserNotFound, RecommendationNotFound{
	}

	public List<Recommendation> getUserRecommendations(String userId) throws UserNotFound{
		return null;
	}  
}