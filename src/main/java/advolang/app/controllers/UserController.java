package advolang.app.controllers;

import java.util.List;

import advolang.app.services.RecommendationService;
import advolang.app.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@Autowired
    private UserService userService;
    
    @Autowired
	private RecommendationService recommendationService;
    
    /**
     * Method that allows to obtain the information related to a specific user.
     * @param username    Identifier of the user who wishes to subscribe, it is expected to be some kind of string that allows its identification.
     * @return  Returns the requested information or an error code as the case may be.
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
     * @param id
     * @return
     */
    @RequestMapping(value = "/users/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateUser(@PathVariable("id") String id) {
        return null;
    }


    /**
     * Method that allows to obtain the recommendations saved by a user.
     * @param id    Identifier of the user who wishes to subscribe, it is expected to be some kind of string that allows its identification.
     * @return  Returns the list of recommendations saved by the user in question or returns an error code depending on the case.
     */
    @RequestMapping(value = "/users/{id}/saved-recommendations", method = RequestMethod.GET)
    public ResponseEntity<?> getSavedRecommendations(@PathVariable("id") String id) {
        try {
            List<Recommendation> listSavedRecommendations = userService.getSavedRecommendations(id);
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
    @RequestMapping(value = "/users/{id}/saved-recommendations", method = RequestMethod.POST)
    public ResponseEntity<?> saveRecommendation(@PathVariable("id") String userId, @RequestParam long recommendationId) {
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
    @RequestMapping(value = "/users/{id}/saved-recommendations", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeSavedRecommendation(@PathVariable("id") String userId, @RequestParam long recommendationId) {
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
     * Get the recommendations created by a specific user.
     * @param userId    Identifier of the user on whom you want to make the request.
     * @return  Returns the recommendations created by that user or an error as the case may be.
     */
    @RequestMapping(value = "/users/{id}/recommendations", method = RequestMethod.GET)
    public ResponseEntity<?> getUserRecommendations(@PathVariable("id") String userId) {
    	    	
        try {
        	User providedUser = this.userService.getUserById(userId);
        
        	if(providedUser == null) {
        		throw(new UserNotFound("This user does not exist"));

        	}
        	return new ResponseEntity<>(this.recommendationService.getUserRecommendations(providedUser), HttpStatus.OK);

        } 
        catch (UserNotFound userNotFound) {
            
        	return new ResponseEntity<>("Error - User not found", HttpStatus.NOT_FOUND);
        } 
        catch (Exception e) {
        
        	return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}