package advolang.app.controllers;

import java.util.List;

import advolang.app.repository.UserRepository;
import advolang.app.services.RecommendationService;
import advolang.app.services.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import advolang.app.exceptions.RecommendationNotFound;
import advolang.app.exceptions.UserNotFound;
import advolang.app.models.Recommendation;
import advolang.app.models.User;

/**
 * UserController
 */
@Controller
@CrossOrigin("*")
@RequestMapping("/api")
public class UserController {
	
	private final UserService userService;
    
    private final RecommendationService recommendationService;

    private final UserRepository userRepository;

    public UserController(UserService userService, RecommendationService recommendationService, UserRepository userRepository) {
        this.userService = userService;
        this.recommendationService = recommendationService;
        this.userRepository = userRepository;
    }

    /**
     * Method that allows to obtain the information related to a specific user.
     * @param username    Identifier of the user who wishes to subscribe, it is expected to be some kind of string that allows its identification.
     * @return  Returns the requested information or an error code as the cse may be.
     */
    @RequestMapping(value = "/users/{username}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable("username") String username) {
        try {
            User user = userService.getUserByUsername(username);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (UserNotFound notFound) {
            return new ResponseEntity<>("Error - User not found", HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }     
    }


    /**
     * Esto tiene que arreglarse.
     * @param username
     * @return
     */
    @RequestMapping(value = "/users/{username}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateUser(@PathVariable("username") String username) {
        return null;
    }


    /**
     * Method that allows to obtain the recommendations saved by a user.
     * @param username    Identifier of the user who wishes to subscribe, it is expected to be some kind of string that allows its identification.
     * @return  Returns the list of recommendations saved by the user in question or returns an error code depending on the case.
     */
    @RequestMapping(value = "/users/{username}/saved-recommendations", method = RequestMethod.GET)
    public ResponseEntity<?> getSavedRecommendations(@PathVariable("username") String username) {
        try {
            List<Recommendation> listSavedRecommendations = userService.getSavedRecommendations(username);
            return new ResponseEntity<>(listSavedRecommendations, HttpStatus.OK);
        } catch (UserNotFound notFound) {
            return new ResponseEntity<>("Error - User not found", HttpStatus.NOT_FOUND);            
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This method allows you to add a new recommendation to a user's saved recommendation list.
     * @param recommendationId  Identifier of the recommendation to be saved within the user's list.
     * @return  Returns a success or error code as appropriate.
     */
    @RequestMapping(value = "/users/{username}/saved-recommendations", method = RequestMethod.POST)
    public ResponseEntity<?> saveRecommendation(@PathVariable("username") String userId, @RequestParam String recommendationId) {
        try {
            userService.saveRecommendation(userId, recommendationId);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error - Bad request", HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * Method that allows to remove a saved recommendation from the user's list.
     * @param userId  Identifier of the user who wants to remove the recommendation from his list.
     * @param recommendationId  Identifier of the recommendation itself, which you wish to remove from the list.
     * @return  Returns a success code or an error code, depending on the case.
     */
    @RequestMapping(value = "/users/{username}/saved-recommendations", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeSavedRecommendation(@PathVariable("username") String userId, @RequestParam("recommendationId") String recommendationId) {
        try {
            userService.removeSavedRecommendation(userId, recommendationId);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        } catch (UserNotFound userNotFound) {
            return new ResponseEntity<>("Error - User not found", HttpStatus.NOT_FOUND);
        } catch(RecommendationNotFound recommendationNotFound){
            return new ResponseEntity<>("Error - Recommendation not found", HttpStatus.NOT_FOUND);
        } catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

    /**
     * Method for determining whether a recommendation has been saved or not
     * @param username	Identifier of a user in the system
     * @param recommendationId	Identifier of a recommendation in the system
     * @return	The boolean value that decides whether the recommendation has been saved or not
     */
    @RequestMapping(value = "/users/{username}/saved-recommendations/{recommendationId}", method = RequestMethod.GET)
    public ResponseEntity<?> didUserSavedThisRecommendation(@PathVariable("username") String username, @PathVariable("recommendationId") String recommendationId) {
        try {
            boolean hasBeenSaved = userService.isSavedThisRecommendation(username, recommendationId);
            return new ResponseEntity<>(hasBeenSaved, HttpStatus.OK);
        } catch(UserNotFound userNotFound){
            return new ResponseEntity<>("Error - User not found", HttpStatus.NOT_FOUND);
        } catch(Exception e){
            return new ResponseEntity<>("Error - Unexpected", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get the recommendations created by a specific user.
     * @param username Identifier of the user on whom you want to make the request.
     * @return  Returns the recommendations created by that user or an error as the case may be.
     */
    @RequestMapping(value = "/users/{username}/recommendations", method = RequestMethod.GET)
    public ResponseEntity<?> getUserRecommendations(@PathVariable("username") String username) {
        try {
            if (!userRepository.existsByUsername(username)){
                throw new UserNotFound("User not found");
            }
        	return new ResponseEntity<>(this.recommendationService.getUserRecommendations(username), HttpStatus.OK);
        } 
        catch (UserNotFound userNotFound) {
        	return new ResponseEntity<>("Error - User not found", HttpStatus.NOT_FOUND);
        } 
        catch (Exception e) {
        	return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}