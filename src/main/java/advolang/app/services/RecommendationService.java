package advolang.app.services;

import advolang.app.exceptions.RecommendationNotFound;
import advolang.app.models.Recommendation;

import java.util.List;
import java.util.Map;

public interface RecommendationService {

    void addRecommendation(String language, Recommendation recommendation);

    List<Recommendation> getRecommendations(String language, Map<String, String> parameters);

    List<Recommendation> getReportedRecommendations(String language);

    Recommendation getSpecificRecommendation(String language, long id) throws RecommendationNotFound;

    List<String> getCategories(String language);

    void addCategory(String language, String category);

    void addSubscription(String language, String userId);

    void removeSubscription(String language, String userId);
}
