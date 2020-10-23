package advolang.app.services.impl;

import advolang.app.exceptions.RecommendationNotFound;
import advolang.app.exceptions.UserNotFound;
import advolang.app.models.Recommendation;
import advolang.app.models.User;
import advolang.app.repository.RecomRepository;
import advolang.app.repository.UserRepository;
import advolang.app.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RecomRepository recommendationRepository;

    @Override
    public void saveUser(User user) {
        this.userRepository.save(user);
    }

    @Override
    public boolean checkExistingUsername(String username) {
        return this.userRepository.existsByUsername(username);
    }

    @Override
    public User getUserById(String userId) throws UserNotFound {

        Optional<User> user = this.userRepository.findById(userId);

        try {
            return user.get();
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public User getUserByUsername(String username) throws UserNotFound {
        Optional<User> user = this.userRepository.findByUsername(username);
        try {
            return user.get();
        } catch (Exception e) {
            throw new UserNotFound("Error - User not found");
        }
    }

    @Override
    public List<Recommendation> getSavedRecommendations(String username) throws UserNotFound {
        // Confirmation of user's existence
        User user = this.getUserByUsername(username);
        List<Recommendation> savedRecommendations = new ArrayList<>();
        // Addind each recommendation from the list to the result
        for (String recommendationId : user.getSavedRecommendations()) {
            if (recommendationRepository.existsById(recommendationId)) {
                Optional<Recommendation> dbRecommendation = recommendationRepository.findById(recommendationId);
                savedRecommendations.add(dbRecommendation.get());
            }
        }
        return savedRecommendations;
    }

    @Override
    public void saveRecommendation(String userId, String recommendationId) throws UserNotFound, RecommendationNotFound {
        // Confirmation of user's existence
        User user = this.getUserByUsername(userId);
        // Confirmation of recommendation's existence
        if( !recommendationRepository.existsById(recommendationId) ){
            throw new RecommendationNotFound("Error - Recommendation not found");
        }
        // Adding a recommendation to a user's saved recommendations
        if (!user.getSavedRecommendations().contains(recommendationId))
            user.getSavedRecommendations().add(recommendationId);
        this.userRepository.save(user);
    }

    @Override
    public void removeSavedRecommendation(String userId, String recommendationId) throws UserNotFound, RecommendationNotFound {
        // Confirmation of user's existence
        User user = this.getUserByUsername(userId);
        // Confirmation of recommendation's existence
        if( !recommendationRepository.existsById(recommendationId) ){
            throw new RecommendationNotFound("Error - Recommendation not found");
        }
        // Remove the recommendation of user's saved list
        List<String> userSavedRecommendation = user.getSavedRecommendations();
        if (userSavedRecommendation.contains(recommendationId)) userSavedRecommendation.remove(recommendationId);
        this.userRepository.save(user);
    }

}

