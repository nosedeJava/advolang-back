package advolang.app.services.impl;

import advolang.app.exceptions.RecommendationNotFound;
import advolang.app.exceptions.UserNotFound;
import advolang.app.models.Recommendation;
import advolang.app.models.User;
import advolang.app.repository.UserRepository;
import advolang.app.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
    private UserRepository userRepository;
	
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
		}
		
		catch (Exception e) {
			return null;
		}
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
    
}

