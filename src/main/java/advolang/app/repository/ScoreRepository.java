package advolang.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import advolang.app.models.Score;

public interface ScoreRepository extends MongoRepository<Score, String> {
	
	public Score findByUserIdAndRecommendationId(String UserId, String RecommendationId);
	
}
