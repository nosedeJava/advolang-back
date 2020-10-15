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
		
		Optional <User> user = this.userRepository.findById(userId);
		
		try {
			return user.get();
		}
		
		catch (Exception e) {
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
		Optional<User> user  = this.userRepository.findByUsername(username);
		try{
			List<Recommendation> savedRecommendations = user.get().getSavedRecommendations();
			return savedRecommendations;
		}	catch(Exception userNotFound) {
			throw new UserNotFound("Error - User not found");
		}
    }

    @Override
    public void saveRecommendation(String userId, String recommendationId) throws UserNotFound, RecommendationNotFound{
		// Confirmation of user's existence
		User user = this.getUserByUsername(userId);
		// Confirmation of recommendation's existence
		Optional<Recommendation> dbRecommendation = this.recommendationRepository.findById(recommendationId);
		Recommendation recommendation;
		try{
			recommendation = dbRecommendation.get();
		}catch(Exception recommendationNotFound) {
			throw new RecommendationNotFound("Error - Recommendation not found");
		}
		// Adding a recommendation to a user's saved recommendations
		if(!user.getSavedRecommendations().contains(recommendation)) user.getSavedRecommendations().add(recommendation);
		this.userRepository.save(user);
    }

    @Override
    public void removeSavedRecommendation(String userId, String recommendationId) throws UserNotFound, RecommendationNotFound {
		// Confirmation of user's existence
		User user = this.getUserByUsername(userId);
		// Confirmation of recommendation's existence
		Optional<Recommendation> dbRecommendation = this.recommendationRepository.findById(recommendationId);
		Recommendation recommendation;
		try{
			recommendation = dbRecommendation.get();
		}catch(Exception recommendationNotFound) {
			throw new RecommendationNotFound("Error - Recommendation not found");
		}
		// Remove the recommendation of user's saved list
		List<Recommendation> userSavedRecommendation = user.getSavedRecommendations();
		if(userSavedRecommendation.contains(recommendation)) userSavedRecommendation.remove(recommendation);
		this.userRepository.save(user);
    }
    
}

