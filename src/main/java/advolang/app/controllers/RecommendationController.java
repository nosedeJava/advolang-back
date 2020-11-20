package advolang.app.controllers;

import advolang.app.exceptions.RecommendationNotFound;
import advolang.app.exceptions.UserNotFound;
import advolang.app.models.Recommendation;
import advolang.app.models.Score;
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
     * Method to get all recommendations that have been created
     * 
     * @return A list with all recommendations
     */
    @RequestMapping(value = "/filter", method = RequestMethod.GET)
    public ResponseEntity<?> getFilterRecommendations(
                        @RequestParam(name = "categories", required = false) List<String> categories,
                        @RequestParam(name = "title", required = false) String title,
                        @RequestParam(name = "difficulty", required = false) String difficulty,
                        @RequestParam(name = "type", required = false) String type,
                        @RequestParam(name = "language", required = false) String language,
                        @RequestParam(name = "user", required = false) String user) {
        try {
            return new ResponseEntity<>(recommendationService.getFilterRecommendations(categories,title,difficulty,type,language,user), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }   

    @RequestMapping(value = "/recommendations", method = RequestMethod.GET)
    public ResponseEntity<?> getAllRecommendations() {
        try {
            return new ResponseEntity<>(recommendationService.getAllRecommendations(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This method returns the recommendations linked to a language (ex: Spanish,
     * English, etc). However, such recommendations can be requested with filters.
     * 
     * @param language Identifier of the language in which the request is made.
     * @return Returns the list of recommendations requested, under the parameters
     *         that have been received.
     */
    @RequestMapping(value = "/{language}/recommendations", method = RequestMethod.GET)
    public ResponseEntity<?> getRecommendations(@PathVariable("language") String language,
            @RequestParam(required = false) Optional<List<String>> categories) {
        try {
            // If a request is made to the reported recommendations a special parameter is
            // received, taking into account flag.
            if (categories.isPresent() && categories.get().contains("reported")) {
                List<Recommendation> listReportedRecommendation = recommendationService
                        .getReportedRecommendations(language);
                return new ResponseEntity<>(listReportedRecommendation, HttpStatus.OK);
            } else {
                List<Recommendation> listRecommendation = recommendationService.getRecommendations(language,
                        categories.orElse(new ArrayList<String>()));
                return new ResponseEntity<>(listRecommendation, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method that allows the registration of a new recommendation about a language.
     * 
     * @return Returns a success or error code as appropriate.
     */
    @RequestMapping(value = "/{language}/recommendations", method = RequestMethod.POST)
    public ResponseEntity<?> addRecommendation(@PathVariable("language") String language,
            @RequestBody Recommendation recommendation) {
        try {
            recommendation.setLanguage(language);
            recommendation.setCreationDate(new Date());
            recommendationService.addRecommendation(recommendation);
            return new ResponseEntity<>("Created", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Unexpected error", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Method to get a specific recommendation by the given id
     * 
     * @param id Id of the recommendation
     * @return A response with the recommendation if exists or an error if not
     */
    @RequestMapping(value = "{language}/{username}/recommendations/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getSpecificRecommendation(@PathVariable("language") String language,
            @PathVariable("username") String username, @PathVariable("id") String id) {
        try {
            Recommendation specificRecommendation = recommendationService.getSpecificRecommendation(username, id);
            return new ResponseEntity<>(specificRecommendation, HttpStatus.OK);
        } catch (RecommendationNotFound recommendationNotFound) {
            return new ResponseEntity<>("Error - Recommendation not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method to get Recommendation thumbnail url
     * 
     * @param language
     * @param id
     * @return
     */
    @RequestMapping(value = "/{language}/recommendations/{id}/thumbnail", method = RequestMethod.GET)
    public ResponseEntity<?> getSpecificRecommendationThumb(@PathVariable("language") String language,
            @PathVariable("id") String id) {
        try {
            Recommendation specificRecommendation = recommendationService.getSpecificRecommendation(language, id);

            return new ResponseEntity<>(specificRecommendation.getThumbnail(), HttpStatus.OK);
        } catch (RecommendationNotFound recommendationNotFound) {
            return new ResponseEntity<>("Error - Recommendation not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method that receives the request for information related to a referral id.
     * 
     * @param language Language in which the requested referral is located.
     * @param id       Identifier of the recommendation itself.
     * @return Returns the information of the requested recommendation or error as
     *         the case may be.
     */
    @RequestMapping(value = "/{language}/recommendations/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getSpecificRecommendation(@PathVariable("language") String language,
            @PathVariable("id") String id) {
        try {
            Recommendation specificRecommendation = recommendationService.getSpecificRecommendation(language, id);
            return new ResponseEntity<>(specificRecommendation, HttpStatus.OK);
        } catch (RecommendationNotFound recommendationNotFound) {
            return new ResponseEntity<>("Error - Recommendation not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Obtain the numerical value of the score related to a specific recommendation
     * 
     * @param language         Language of the recommendation
     * @param recommendationId Recommendation identifier
     * @return Return the score
     */
    @RequestMapping(value = "/{language}/recommendations/{id}/score", method = RequestMethod.GET)
    public ResponseEntity<?> getScoreOfRecommendation(@PathVariable("language") String language,
            @PathVariable("id") String recommendationId) {
        try {
            Double score = recommendationService.getScoreOfRecommendation(language, recommendationId);
            return new ResponseEntity<>(score, HttpStatus.OK);
        } catch (RecommendationNotFound recommendationNotFound) {
            return new ResponseEntity<>("Error - Recommendation not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Unexpected error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Register or update a score made by a specific user.
     * 
     * @param language         Language in which the recommendation is made
     * @param recommendationId Internal recommendation identifier
     * @return Returns the numerical value of the new weighted score or error
     */
    @RequestMapping(value = "/{language}/recommendations/{id}/score", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateRate(@PathVariable("language") String language,
            @PathVariable("id") String recommendationId, @RequestBody Score newScore) {
        try {
            Double score = recommendationService.rateRecommendation(language, recommendationId, newScore);
            return new ResponseEntity<>(score, HttpStatus.OK);
        } catch (RecommendationNotFound recommendationNotFound) {
            return new ResponseEntity<>("Error - Recommendation not found", HttpStatus.NOT_FOUND);
        } catch (UserNotFound userNotFound) {
            return new ResponseEntity<>("Error - User not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error - Bad request", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * This method performs the registration of a new subscription of a user to a
     * specific language.
     * 
     * @param language Identifier of the language on which the request is made.
     * @param userId   Identifier of the user that wishes to subscribe, it is
     *                 expected to be some kind of string that allows its
     *                 identification.
     * @return Returns a success code or an error code as the case may be.
     */
    @RequestMapping(value = "/{language}/subscription", method = RequestMethod.POST)
    public ResponseEntity<?> addSubscription(@PathVariable("language") String language,
            @RequestParam("username") String userId) {
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
     * 
     * @param language Identifier of the language on which the request is made.
     * @param userId   Identifier of the user that wishes to subscribe, it is
     *                 expected to be some kind of string that allows its
     *                 identification.
     * @return Returns a success code or an error code as the case may be.
     */
    @RequestMapping(value = "/{language}/subscription", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeSubscription(@PathVariable("language") String language,
            @RequestParam("username") String userId) {
        try {
            recommendationService.removeSubscription(language, userId);
            return new ResponseEntity<>("Ok", HttpStatus.OK);
        } catch (UserNotFound e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
