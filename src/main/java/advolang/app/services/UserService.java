package advolang.app.services;

import org.springframework.stereotype.Service;

import advolang.app.exception.RecommendationNotFound;
import advolang.app.exception.UserBadRequest;
import advolang.app.exception.UserNotFound;
import advolang.app.model.User;

/**
 * UserService
 */
@Service
public class UserService {

    public void createAccount(User user) throws UserBadRequest{
	}

	public void getUser(String id) throws UserNotFound{
	}

	public void getSavedRecommendations(String id) throws UserNotFound{
	}

	public void saveRecommendation(String userId, long recommendationId) {
	}

	public void removeSavedRecommendation(String userId, long recommendationId) throws UserNotFound, RecommendationNotFound{
	}

	public void getUserRecommendations(String userId) throws UserNotFound{
	}  
}