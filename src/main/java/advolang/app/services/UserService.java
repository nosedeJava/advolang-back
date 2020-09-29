package advolang.app.services;

import advolang.app.exceptions.RecommendationNotFound;
import advolang.app.exceptions.UserNotFound;
import advolang.app.models.Recommendation;
import advolang.app.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> getUserByUsername(String username) throws UserNotFound;

    List<Recommendation> getSavedRecommendations(String id) throws UserNotFound;

    public void saveRecommendation(String userId, long recommendationId);

    public void removeSavedRecommendation(String userId, long recommendationId) throws UserNotFound, RecommendationNotFound;

    List<Recommendation> getUserRecommendations(String userId) throws UserNotFound;
}
