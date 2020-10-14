package advolang.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import advolang.app.models.Recommendation;
import advolang.app.models.Score;
import advolang.app.services.ScoreService;

@Controller
@CrossOrigin("*")
@RequestMapping("/api")
public class ScoreController {
	
	@Autowired
	private ScoreService scoreService;
	
	@RequestMapping(value = "/scores/add", method = RequestMethod.POST)
    public ResponseEntity<?> addRecommendation(@RequestBody Score score) {
		System.out.println(score.toString());
		try {
			this.scoreService.addScore(score);
            return new ResponseEntity<>("Created", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Unexpected error", HttpStatus.BAD_REQUEST);
        }
	}
	
	@RequestMapping(value = "/scores/{userId}/{recommendationId}", method = RequestMethod.GET)
    public ResponseEntity<?> getRecommendations(@PathVariable("userId") String userId, @PathVariable("recommendationId") String recommendationId) {
        try{
        	return new ResponseEntity<>(this.scoreService.checkIfExistsUserScore(userId, recommendationId), HttpStatus.OK);
            
        } catch(Exception e){
        	System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
	
	
	
	
