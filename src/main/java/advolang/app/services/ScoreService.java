package advolang.app.services;

import java.util.List;

import advolang.app.models.Score;

public interface ScoreService {
	
	/**
	 * 
	 * @param score
	 */
	public void addScore(Score score);
	
	/**
	 * 
	 * @param userId
	 * @param recommendationId
	 * @return
	 */
	public Score getScoreByUserAndRecom (String userId, String recommendationId);

	/**
	 * 
	 * @param scoreId
	 * @return
	 */
	public Score getScoreById(String scoreId);
	
	/**
	 * 
	 * @param recommendationId
	 * @return
	 */
	public List<Score> getScoresSpecificRecom (String recommendationId);
	
	/**
	 * 
	 * @param recommendationId
	 * @return
	 */
	public List<Double> getScoresValuesSpecificRecom(String recommendationId);
	
	/**
	 * 
	 * @param userId
	 * @param recommendationId
	 * @return
	 */
	public Boolean checkIfExistsUserScore (String userId, String recommendationId);

	/**
	 * 
	 * @param newScore
	 */
	public void updateScoreValue(Score newScore);
	
	
}
