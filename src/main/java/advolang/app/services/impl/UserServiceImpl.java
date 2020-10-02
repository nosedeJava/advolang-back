package advolang.app.services.impl;

import advolang.app.exceptions.RecommendationNotFound;
import advolang.app.exceptions.UserNotFound;
import advolang.app.models.Recommendation;
import advolang.app.models.User;
import advolang.app.repository.UserRepository;
import advolang.app.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> getUserByUsername(String username) throws UserNotFound {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<Recommendation> getSavedRecommendations(String id) throws UserNotFound {
        return null;
    }

    @Override
    public void saveRecommendation(String userId, long recommendationId) {

    }

    @Override
    public void removeSavedRecommendation(String userId, long recommendationId) throws UserNotFound, RecommendationNotFound {

    }

    @Override
    public List<Recommendation> getUserRecommendations(String userId) throws UserNotFound {
        return null;
    }
}
