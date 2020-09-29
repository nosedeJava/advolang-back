package advolang.app.controller;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

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
public class UserController {

    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Metodo que permite la obtención de la información relacionada a un usuario en especifico.
     * @param username    Identificador del usuario que desea suscribirse, se espera sea algún tipo de cadena que permita su identificación.
     * @return  Retorna la información solicitada o un código de error según sea el caso.
     */
    @RequestMapping(value = "/users/{username}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable("username") String username) {
        try {
            Optional<User> user = userService.getUserByUsername(username);
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
     * Metodo que permite la obtención de las recomendaciones guardadas por un usuario.
     * @param id    Identificador del usuario que desea suscribirse, se espera sea algún tipo de cadena que permita su identificación.
     * @return  Retorna la lista de recomendaciones guardadas por el usuario en cuestión o devuelve un código de error dependiendo del caso.
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
     * Este metodo permite el agregar una nueva recomendación a la lista de recomendaciones guardadas de un usuario.
     * @param recommendationId  Identificador de la recomendación que se desea guardar dentro de la lista del usuario.
     * @return  Retorna un código de éxito o uno de error según sea el caso.
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
     * Metodo que permite remover una recomendación guardada de la lista del usuario.
     * @param userId  Identificador del usuario que desea eliminar la recomendación de su lista.
     * @param recommendationId  Identificador de la recomendación en sí, que se desea remover de la lista.
     * @return  Retorna un código de éxito o uno de error según sea el caso.
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
     * Obtiene las recomendaciones creadas por un usuario especifico.
     * @param userId    Identificador del usuario sobre el cual se desea realizar la petición.
     * @return  Retorna las recomendaciones creadas por dicho usuario o un error según sea el caso.
     */
    @RequestMapping(value = "/users/{id}/recommendations", method = RequestMethod.GET)
    public ResponseEntity<?> getUserRecommendations(@PathVariable("id") String userId) {
        try {
            List<Recommendation> listUserRecommendations = userService.getUserRecommendations(userId);
            return new ResponseEntity<>(listUserRecommendations, HttpStatus.OK);
        } catch (UserNotFound userNotFound) {
            return new ResponseEntity<>("Error - User not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}