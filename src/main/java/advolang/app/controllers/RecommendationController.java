package advolang.app.controllers;

import advolang.app.exceptions.RecommendationNotFound;
import advolang.app.exceptions.UserNotFound;
import advolang.app.models.Recommendation;
import advolang.app.services.RecommendationService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * RecommendationsController
 */
@Controller
@CrossOrigin("*")
@RequestMapping("/api")
public class RecommendationController {

	@Autowired
	private RecommendationService recommendationService;

    /**
     * This method returns the recommendations linked to a language (ex: Spanish, English, etc).
     * However, such recommendations can be requested with filters.
     * @param language  Identifier of the language in which the request is made.
     * @return  Returns the list of recommendations requested, under the parameters that have been received.
     */
    @RequestMapping(value = "/{language}/recommendations", method = RequestMethod.GET)
    public ResponseEntity<?> getRecommendations(@PathVariable("language") String language, @RequestParam(required = false) Optional<List<String>> categories) {
        try{
            //If a request is made to the reported recommendations a special parameter is received, taking into account flag.
            if(categories.isPresent() && categories.get().contains("reported")){
                List<Recommendation> listReportedRecommendation = recommendationService.getReportedRecommendations(language);
                return new ResponseEntity<>(listReportedRecommendation, HttpStatus.OK);
            }else{
                List<Recommendation> listRecommendation = recommendationService.getRecommendations(language, categories.orElse(new ArrayList<String>()));
                return new ResponseEntity<>(listRecommendation, HttpStatus.OK);
            }
        } catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method that allows the registration of a new recommendation about a language.
     * @return  Returns a success or error code as appropriate.
     */
    @RequestMapping(value = "/{language}/recommendations", method = RequestMethod.POST)
    public ResponseEntity<?> addRecommendation(@PathVariable("language") String language, @RequestBody Recommendation recommendation){
        try {
            recommendation.setLanguage(language);
            recommendation.setCreationDate(new Date());
            recommendationService.addRecommendation(recommendation);
            return new ResponseEntity<>("Created", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Unexpected error", HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * Method that receives the request for information related to a referral id.
     * @param language  Language in which the requested referral is located.
     * @param id    Identifier of the recommendation itself.
     * @return  Returns the information of the requested recommendation or error as the case may be.
     */
    @RequestMapping(value = "/{language}/recommendations/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getSpecificRecommendation(@PathVariable("language") String language, @PathVariable("id") String id){
        try {
            Recommendation specificRecommendation = recommendationService.getSpecificRecommendation(language, id);
            return new ResponseEntity<>(specificRecommendation, HttpStatus.OK);
        } catch (RecommendationNotFound recommendationNotFound) {
            return new ResponseEntity<>("Error - Recommendation not found",HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Revisar
     * @param language
     * @param id
     * @return
     */
    @RequestMapping(value = "/{language}/recommendations/{id}/rate", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateRate(@PathVariable("language") String language, @PathVariable("id") long id) {
        try {
            return null;           
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * This method is in charge of returning the categories related to a specific language.
     * @param language  Identifier of the language on which the request is made.
     * @return  Returns the list of categories or an error if it occurs.
     */
    @RequestMapping(value = "/{language}/categories", method = RequestMethod.GET)
    public ResponseEntity<?> getCategories(@PathVariable("language") String language) {
        try {
            List<String> categories = recommendationService.getCategories(language);
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }    
    }


    /**
     * Method in charge of receiving the requests of creation of a category on a specific language.
     * @param language  Identifier of the language on which the request is made.
     * @param category  Category in question, in this case a string containing the "name" of the category is expected.
     * @return  Returns a success code or an error code as the case may be.
     */
    @RequestMapping(value = "/{language}/categories", method = RequestMethod.POST)
    public ResponseEntity<?> addCategory(@PathVariable("language") String language, @RequestParam("category") String category) {
        try {
            recommendationService.addCategory(language, category);
            return new ResponseEntity<>("Created", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * This method performs the registration of a new subscription of a user to a specific language.
     * @param language  Identifier of the language on which the request is made.
     * @param userId    Identifier of the user that wishes to subscribe, it is expected to be some kind of string that allows its identification.
     * @return  Returns a success code or an error code as the case may be.
     */
    @RequestMapping(value = "/{language}/subscription", method = RequestMethod.POST)
    public ResponseEntity<?> addSubscription(@PathVariable("language") String language, @RequestParam("username") String userId) {
        try {
            recommendationService.addSubscription(language, userId);
            return new ResponseEntity<>("Ok", HttpStatus.OK);
        } catch (UserNotFound e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    /**
     * Method of removing a user's subscription to a specific language.
     * @param language  Identifier of the language on which the request is made.
     * @param userId    Identifier of the user that wishes to subscribe, it is expected to be some kind of string that allows its identification.
     * @return  Returns a success code or an error code as the case may be.
     */
    @RequestMapping(value = "/{language}/subscription", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeSubscription(@PathVariable("language") String language, @RequestParam("username") String userId) {
        try {
            recommendationService.removeSubscription(language, userId);
            return new ResponseEntity<>("Ok", HttpStatus.OK);
        } catch (UserNotFound e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
