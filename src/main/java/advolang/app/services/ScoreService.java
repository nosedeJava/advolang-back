package advolang.app.services;

import advolang.app.models.Score;

public interface ScoreService {
	
	public void addScore(Score score);
	
	public Score getScoreByUserAndRecom (String userId, String recommendationId);
	
	public Boolean checkIfExistsUserScore (String userId, String recommendationId);
	
	
}
