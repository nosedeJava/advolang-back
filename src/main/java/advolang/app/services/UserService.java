package advolang.app.services;

import java.util.List;

import org.springframework.stereotype.Service;

import advolang.app.exception.RecommendationNotFound;
import advolang.app.exception.UserBadRequest;
import advolang.app.exception.UserNotFound;
import advolang.app.model.User;
import advolang.app.model.Recommendation;

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