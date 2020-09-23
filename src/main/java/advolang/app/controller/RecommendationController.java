package advolang.app.controller;

import advolang.app.services.RecommendationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * RecommendationsController
 */
@RestController
public class RecommendationController {

    @Autowired
    RecommendationService recommendationService;

    @RequestMapping(value = "/{language}/recommendations", method = RequestMethod.POST)
    public ResponseEntity<?> addRecommendation(@PathVariable("language") String language) {
        try {
            recommendationService.addRecommendation(language);
            return new ResponseEntity<>("Created", HttpStatus.CREATED);
        } catch (Exception e) {
            //TODO: handle exception
        }
        return null;
    }
}