package advolang.app.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import advolang.app.models.Score;
import advolang.app.repository.ScoreRepository;
import advolang.app.services.ScoreService;

@Service
public class ScoreServiceImpl implements ScoreService {
	
	@Autowired
	private ScoreRepository scoreRepository;

	@Override
	public void addScore(Score score) {
		this.scoreRepository.save(score);
	}

	@Override
	public Score getScoreByUserAndRecom(String userId, String recommendationId ) {
		return this.scoreRepository.findByUserIdAndRecommendationId(userId, recommendationId);
	}
	
	@Override
	public Score getScoreById (String scoreId) {
		return this.scoreRepository.findById(scoreId).get();
	}
	
	@Override
	public List<Score> getScoresSpecificRecom(String recommendationId) {
		return this.scoreRepository.findByRecommendationId(recommendationId);
	}

	@Override
	public List<Double> getScoresValuesSpecificRecom(String recommendationId) {
		
		List<Score> scores = this.scoreRepository.findByRecommendationId(recommendationId);
		List<Double> scoresValues= new ArrayList<Double>();
		
		for(Score score : scores) {
			scoresValues.add(score.getValue());
		}
		
		return scoresValues;
	}
	
	@Override
	public Boolean checkIfExistsUserScore(String userId, String recommendationId) {
		return this.scoreRepository.findByUserIdAndRecommendationId(userId, recommendationId) == null ? false : true;
	}
	
	@Override
	public void updateScoreValue(Score newScore) {
		this.scoreRepository.save(newScore);
	}

}
