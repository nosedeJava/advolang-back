package advolang.app.services.impl;

import advolang.app.exceptions.RecommendationNotFound;
import advolang.app.exceptions.UserNotFound;
import advolang.app.models.Category;
import advolang.app.models.Recommendation;
import advolang.app.models.Score;
import advolang.app.models.User;
import advolang.app.repository.CategoryRepository;
import advolang.app.repository.RecomRepository;
import advolang.app.repository.ScoreRepository;
import advolang.app.repository.UserRepository;
import advolang.app.services.RecommendationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecommendationServiceImpl implements RecommendationService {

    @Autowired
    private RecomRepository recomRepository;

    @Autowired
    private CategoryRepository catRepo;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ScoreRepository scoreRepository;

    /**
     * When a recommendations is added, update to each category its popularity
     */

    @Override
    public void addRecommendation(Recommendation recommendation) throws RecommendationNotFound {
        try {
            this.recomRepository.save(recommendation);
            for (Category category : recommendation.getCategories()) {
                Category ct=catRepo.findByValue(category.getValue()).get();
                ct.setPopularity(ct.getPopularity()+1);
                catRepo.save(ct);
            }
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
	public List<Recommendation> getAllRecommendations() throws RecommendationNotFound {
    	try {
            return this.recomRepository.findAll();
        } catch (Exception e) {
            throw new RecommendationNotFound("Failed find recommendations");
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
    public Recommendation getSpecificRecommendationWithUser(String username, String id) throws RecommendationNotFound {
        try {
            return recomRepository.findByIdAndCreator(id, username).get();
        } catch (Exception e) {
            throw new RecommendationNotFound("Recommendation not found");
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
    public void addSubscription(String language, String username) throws UserNotFound {
        try {
            Optional<User> us= userRepository.findByUsername(username);
            if(!us.isPresent())throw new UserNotFound("No user exists with that username");
            User user = us.get();
            if(user.getSubscriptions().contains(language)) throw new UserNotFound("This user is already subscribed to this language");
            user.getSubscriptions().add(language);
            userRepository.save(user);
        } catch (Exception e) {
            throw new UserNotFound("Subscription failed");
        }
    }

    @Override
    public void removeSubscription(String language, String username) throws UserNotFound{
        try {
            Optional<User> us= userRepository.findByUsername(username);
            if(!us.isPresent())throw new UserNotFound("No user exists with that username");
            User user = us.get();
            if(!user.getSubscriptions().contains(language)) throw new UserNotFound("This user hasn't suscribed to this language");
            user.getSubscriptions().remove(language);
            userRepository.save(user);
        } catch (Exception e) {
            throw new UserNotFound("Remove subscription failed");
        }
    }

    @Override
    public Double getScoreOfRecommendation(String language, String recommendationId) throws RecommendationNotFound {
        // Confirmation of recommendation's existence
        if(!recomRepository.existsById(recommendationId)) throw new RecommendationNotFound("Error - Recommendation not found");
        // Calculation of the score
        List<Score> scores = scoreRepository.findAllByRecommendationId(recommendationId);
        float sum = 0;
        for(Score score : scores){
            sum+=score.getValue();
        }
        if(sum==0) return Double.valueOf("0");
        else {
            float average = sum / scores.size();
            Double result = Double.valueOf(average);
            return result;
        }
    }

    @Override
    public Double rateRecommendation(String language, String recommendationId, Score newScore) throws RecommendationNotFound, UserNotFound, Exception {
        // Confirmation of recommendation's existence and the consistent relationship
        if(!recomRepository.existsById(recommendationId)) throw new RecommendationNotFound("Error - Recommendation not found");
        if(!recommendationId.equals(newScore.getRecommendationId())) throw new Exception("Error - Inconsistent information");
        // Confirmation of user's existence
        if(!userRepository.existsById(newScore.getUserId())) throw new UserNotFound("Error - User not found");
        // Confirmation of score's existence
        Score previousScore = null;
        // If the user has previously scored
        try {
            previousScore = scoreRepository.findByUserAndRecommendation(newScore.getUserId(), newScore.getRecommendationId()).get();
            previousScore.setValue(newScore.getValue());
            scoreRepository.save(previousScore);
        }
        // In case the score is new
        catch(Exception e) {
            scoreRepository.save(newScore);
        }
        return getScoreOfRecommendation(language, recommendationId);
    }
    
}
