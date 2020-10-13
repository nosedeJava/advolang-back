package advolang.app.services.impl;

import advolang.app.exceptions.RecommendationNotFound;
import advolang.app.exceptions.UserNotFound;
import advolang.app.models.Recommendation;
import advolang.app.repository.CategoryRepository;
import advolang.app.repository.RecomRepository;
import advolang.app.services.RecommendationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendationServiceImpl implements RecommendationService {

    @Autowired
    private RecomRepository recomRepository;

    @Autowired
    private CategoryRepository catRepo;

    @Override
    public void addRecommendation(Recommendation recommendation) throws RecommendationNotFound {
        try {
            this.recomRepository.save(recommendation);
            recommendation.getCategories().forEach(category -> {
                category.setPopularity(category.getPopularity()+1);
                catRepo.save(category);
            });
        } catch (Exception e) {
            throw new RecommendationNotFound("Failed to create a recommendations");
        }
    }

    @Override
    public List<Recommendation> getUserRecommendations(String creator) throws UserNotFound {
        try {
            return this.recomRepository.findByCreator(creator);
        } catch (Exception e) {
            throw new UserNotFound("No recommendations found for this user");
        }

    }

    @Override
    public List<Recommendation> getRecommendations(String language, List<String> values) throws RecommendationNotFound {
        try {
            List<Recommendation> recommendations = recomRepository.findByLanguage(language);
            return recommendations;
        } catch (Exception e) {
            throw new RecommendationNotFound("Failed query on recommendations by language");
        }
    }

    @Override
    public List<Recommendation> getReportedRecommendations(String language) throws RecommendationNotFound {
        try {
            return recomRepository.findByReportedIsTrueAndLanguage(language);
        } catch (Exception e) {
            throw new RecommendationNotFound("Failed query on reported recommendations by language");
        }
    }

    @Override
    public Recommendation getSpecificRecommendation(String language, String id) throws RecommendationNotFound {
        try {
            return recomRepository.findById(id).get();
        } catch (Exception e) {
            throw new RecommendationNotFound("Recommendation not found");
        }
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
