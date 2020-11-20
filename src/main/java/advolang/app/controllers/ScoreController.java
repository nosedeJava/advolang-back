package advolang.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import advolang.app.models.Score;
import advolang.app.services.ScoreService;

@Controller
@CrossOrigin("*")
@RequestMapping("/api")
public class ScoreController {
	
	@Autowired
	private ScoreService scoreService;
	
	/**
	 * 
	 * @param score
	 * @return
	 */
	@RequestMapping(value = "/scores/add", method = RequestMethod.POST)
    public ResponseEntity<?> addScoreRecommendation(@RequestBody Score score) {
		System.out.println(score.toString());
		try {
			this.scoreService.addScore(score);
            return new ResponseEntity<>("Created", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Unexpected error", HttpStatus.BAD_REQUEST);
        }
	}
	
	/**
	 * 
	 * @param userId
	 * @param recommendationId
	 * @return
	 */
	@RequestMapping(value = "/scores/{userId}/{recommendationId}", method = RequestMethod.GET)
    public ResponseEntity<?> getIfExistScoreUserRecommendation(@PathVariable("userId") String userId, @PathVariable("recommendationId") String recommendationId) {
        try{
        	return new ResponseEntity<>(this.scoreService.checkIfExistsUserScore(userId, recommendationId), HttpStatus.OK);
            
        } catch(Exception e){
        	System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	/**
	 * 
	 * @param userId
	 * @param recommendationId
	 * @return
	 */
	@RequestMapping(value = "/scores/values/{userId}/{recommendationId}", method = RequestMethod.GET)
	public ResponseEntity<?> getScoreUserRecommendation(@PathVariable("userId") String userId, @PathVariable("recommendationId") String recommendationId) {
        try{
        	return new ResponseEntity<>(this.scoreService.getScoreByUserAndRecom(userId, recommendationId), HttpStatus.OK);
            
        } catch(Exception e){
        	System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	/**
	 * 
	 * @param score
	 * @return
	 */
	@RequestMapping(value = "/scores/value/update", method = RequestMethod.POST)
	public ResponseEntity<?> updateScoreUserRecommendation(@RequestBody Score score) {
        try{
        	this.scoreService.updateScoreValue(score);
        	return new ResponseEntity<>(this.scoreService.getScoresValuesSpecificRecom(score.getRecommendationId()), HttpStatus.OK);
            
        } catch(Exception e){
        	System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	@RequestMapping(value = "/scores/values/{recommendationId}", method = RequestMethod.GET)
	public ResponseEntity<?> getRecommendationScoresValues(@PathVariable("recommendationId") String recommendationId) {
        try{
        	return new ResponseEntity<>(this.scoreService.getScoresValuesSpecificRecom(recommendationId), HttpStatus.OK);
            
        } catch(Exception e){
        	System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	
}
	
	
	
	
