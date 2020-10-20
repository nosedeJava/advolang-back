package advolang.app.repository;
import advolang.app.models.Score;

import advolang.app.models.Score;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface ScoreRepository extends MongoRepository<Score, String> {
	
@Repository
public interface ScoreRepository extends MongoRepository<Score, String> {

    @Query(value = "{'userId' : ?0, 'recommendationId' : ?1 }")
    public Optional<Score> findByUserAndRecommendation(String userId, String recommendationId);
    
    public List<Score> findAllByRecommendationId(String recommendationId);
    
    public Score findByUserIdAndRecommendationId(String UserId, String RecommendationId);

    public Optional<Score> findById (String id);
	
	  @Query(value = "{'recommendationId' : ?0 }")
	  public List<Score> findByRecommendationId(String recommendationId);

}
