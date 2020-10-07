package advolang.app.services.impl;

import advolang.app.exceptions.RecommendationNotFound;
import advolang.app.exceptions.UserNotFound;
import advolang.app.models.Recommendation;
import advolang.app.models.User;
import advolang.app.repository.RecomRepository;
import advolang.app.services.RecommendationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RecommendationServiceImpl implements RecommendationService {
	
	@Autowired
	private RecomRepository recomRepository;
	
    @Override
    public void addRecommendation(Recommendation recommendation) {
		this.recomRepository.save(recommendation);
    }
    
    @Override
    public List<Recommendation> getUserRecommendations(User creator) throws UserNotFound{
    	return this.recomRepository.findByCreator(creator);
    }
    
    @Override
    public List<Recommendation> getRecommendations(String language, Map<String, String> parameters) {
        return null;
    }

    @Override
    public List<Recommendation> getReportedRecommendations(String language) {
        return null;
    }

    @Override
    public Recommendation getSpecificRecommendation(String language, String id) throws RecommendationNotFound {
        return null;
    }

    @Override
    public List<String> getCategories(String language) {
        return null;
    }

    @Override
    public void addCategory(String language, String category) {

    }

    @Override
    public void addSubscription(String language, String userId) {

    }

    @Override
    public void removeSubscription(String language, String userId) {

    }
}
