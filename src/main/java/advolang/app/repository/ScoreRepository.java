package advolang.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import advolang.app.models.Score;

public interface ScoreRepository extends MongoRepository<Score, String> {
	
	public Score findByUserIdAndRecommendationId(String UserId, String RecommendationId);
	
	public Optional<Score> findById (String id);
	
	@Query(value = "{'recommendationId' : ?0 }")
	public List<Score> findByRecommendationId(String recommendationId);
}
