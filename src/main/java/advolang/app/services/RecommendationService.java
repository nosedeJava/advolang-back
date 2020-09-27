package advolang.app.services;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import advolang.app.exceptions.RecommendationNotFound;
import advolang.app.models.Recommendation;

/**
 * RecommendationService
 */
@Service
public class RecommendationService {

	public void addRecommendation(String language, Recommendation recommendation) {
	}

	public List<Recommendation> getRecommendations(String language, Map<String, String> parameters) {
		return null;
	}
	
	public List<Recommendation> getReportedRecommendations(String language) {
		return null;		
	}

	public Recommendation getSpecificRecommendation(String language, long id) throws RecommendationNotFound{
		return null;
	}

	public List<String> getCategories(String language) {
		return null;
	}

	public void addCategory(String language, String category) {
	}

	public void addSubscription(String language, String userId) {
	}

	public void removeSubscription(String language, String userId) {
	}
}