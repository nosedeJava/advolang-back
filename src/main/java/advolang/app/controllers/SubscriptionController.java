package advolang.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import advolang.app.exceptions.UserNotFound;
import advolang.app.services.UserService;

/**
 * SubscriptionController
 */
@Controller
@CrossOrigin("*")
@RequestMapping("/api")
public class SubscriptionController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/users/{username}/subscriptions", method = RequestMethod.GET)
    public ResponseEntity<?> getUserSubscriptions(@PathVariable("username") String username) {
        try {
            List<String> subsctiptions = userService.getUserSubscriptions(username);
            return new ResponseEntity<>(subsctiptions, HttpStatus.OK);
        } catch (UserNotFound notFound) {
            return new ResponseEntity<>("Error - User not found", HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }     
    }
}
