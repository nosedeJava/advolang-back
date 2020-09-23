package advolang.app.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import advolang.app.exception.RecommendationNotFound;
import advolang.app.exception.UserBadRequest;
import advolang.app.exception.UserNotFound;
import advolang.app.model.User;
import advolang.app.services.UserService;

/**
 * UserController
 */
@RestController
public class UserController {

    @Autowired
    UserService userService;


    /**
     * 
     * @param user
     * @return
     */
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<?> createAccount(@RequestBody User user) {
        try {
            userService.createAccount(user);
            return new ResponseEntity<>("Created", HttpStatus.CREATED);
        } catch (UserBadRequest badRequest) {
            Logger.getLogger(UserController.class.getName()).log(Level.FINE, "User error", badRequest);
            return new ResponseEntity<>("Error - Email in use", HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, "Unexcepted error", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    /**
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable("id") String id) {
        try {
            userService.getUser(id);
            return new ResponseEntity<>("Ok", HttpStatus.OK);
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
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = "/users/{id}/saved-recommendations", method = RequestMethod.GET)
    public ResponseEntity<?> getSavedRecommendations(@PathVariable("id") String id) {
        try {
            userService.getSavedRecommendations(id);
            return new ResponseEntity<>("Ok", HttpStatus.OK);
        } catch (UserNotFound notFound) {
            return new ResponseEntity<>("Error - User not found", HttpStatus.NOT_FOUND);            
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 
     * @param userId
     * @param recommendationId
     * @return
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
     * 
     * @param userId
     * @param recommendationId
     * @return
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
     * 
     * @param userId
     * @return
     */
    @RequestMapping(value = "/users/{id}/recommendations", method = RequestMethod.GET)
    public ResponseEntity<?> getUserRecommendations(@PathVariable("id") String userId) {
        try {
            userService.getUserRecommendations(userId);
            return new ResponseEntity<>("Ok", HttpStatus.OK);
        } catch (UserNotFound userNotFound) {
            return new ResponseEntity<>("Error - User not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}