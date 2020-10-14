package advolang.app.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
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
		scoreRepository.save(score);
	}

	@Override
	public Score getScoreByUserAndRecom(String userId, String recommendationId ) {
		return this.scoreRepository.findByUserIdAndRecommendationId(userId, recommendationId);
	}

	@Override
	public Boolean checkIfExistsUserScore(String userId, String recommendationId) {
		return this.scoreRepository.findByUserIdAndRecommendationId(userId, recommendationId) == null ? false : true;
	}
	
	
	
	

}
