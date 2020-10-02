package advolang.app.controllers;

import advolang.app.exceptions.RecommendationNotFound;
import advolang.app.models.Recommendation;
import advolang.app.services.RecommendationService;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * RecommendationsController
 */
@Controller
public class RecommendationController {

    final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    /**
     * Este metodo retorna las recomendaciones ligadas a un idioma (Ej: Español, English, etc).
     * Sin embargo, dichas recomendaciones pueden ser solicitadas con filtros.
     * @param language  Identificador del idioma sobre el cual se hace la petición.
     * @param parameters    Parametros de filtrado.
     * @return  Retorna la lista de recomendaciones solicitada, bajo los parametros que se hayan recibido.
     */
    @RequestMapping(value = "/{language}/recommendations", method = RequestMethod.GET)
    public ResponseEntity<?> getRecommendations(@PathVariable("language") String language, @RequestParam Map<String, String> parameters) {
        try{
            // Si se realiza una petición a las recomendaciones reportadas se recibe un parametro especial, teniendo en cuenta de flag.
            if(parameters.containsKey("reported") && parameters.get("reported").compareTo("true")==0){
                List<Recommendation> listReportedRecommendation = recommendationService.getReportedRecommendations(language);
                return new ResponseEntity<>(listReportedRecommendation, HttpStatus.OK);
            }else{
                List<Recommendation> listRecommendation = recommendationService.getRecommendations(language, parameters);
                return new ResponseEntity<>(listRecommendation, HttpStatus.OK);
            }
        } catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Metodo que permite el registro de una nueva recomendación sobre un idioma.
     * @param language  Identificador del idioma sobre el cual se realiza la petición.
     * @return  Retorna un código de éxito o de error según sea el caso.
     */
    @RequestMapping(value = "/{language}/recommendations", method = RequestMethod.POST)
    public ResponseEntity<?> addRecommendation(@PathVariable("language") String language, @RequestBody Recommendation recommendation){
        try {
            recommendationService.addRecommendation(language, recommendation);
            return new ResponseEntity<>("Created", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Unexpected error", HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * Metodo que recibe la solicitud de la información relacionada a un id de recomendación.
     * @param language  Lenguaje en el que se encuentra la recomendación solicitada.
     * @param id    Identificador de la recomendación en sí.
     * @return  Retorna la información de la recomendación solicitada o error según sea el caso.
     */
    @RequestMapping(value = "/{language}/recommendations/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getSpecificRecommendation(@PathVariable("language") String language, @PathVariable("id") long id){
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
     * Este metodo es el encargado de retornar las categorias relacionadas a un idioma en especifico.
     * @param language  Identificador del idioma sobre el cual se realiza la petición.
     * @return  Retorna la lista de categorias o un error si llegase a ocurrir.
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
     * Metodo encargado de recibir las peticiones de creación de una categoria sobre un idioma especifico.
     * @param language  Identificador del idioma sobre el cual se realiza la petición.
     * @param category  Categoría en cuestión, en este caso se espera una cadena que contenga el "nombre" de la categoria.
     * @return  Retorna un código de éxito o un código de error según sea el caso.
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
     * Este metodo realiza el registro de una nueva suscripción de un usuario hacia un idioma especifico.
     * @param language  Identificador del idioma sobre el cual se realiza la petición.
     * @param userId    Identificador del usuario que desea suscribirse, se espera sea algún tipo de cadena que permita su identificación.
     * @return  Devuelve un código de éxito o un código de error según sea el caso.
     */
    @RequestMapping(value = "/{language}/subscription", method = RequestMethod.POST)
    public ResponseEntity<?> addSubscription(@PathVariable("language") String language, @RequestParam("user") String userId) {
        try {
            recommendationService.addSubscription(language, userId);
            return new ResponseEntity<>("Ok", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    /**
     * Metodo encargado de remover la suscripción de un usuario hacia un idioma en especifico.
     * @param language  Identificador del idioma sobre el cual se realiza la petición.
     * @param userId    Identificador del usuario que desea suscribirse, se espera sea algún tipo de cadena que permita su identificación.
     * @return  Devuelve un código de éxito o un código de error según sea el caso.
     */
    @RequestMapping(value = "/{language}/subscription", method = RequestMethod.GET)
    public ResponseEntity<?> removeSubscription(@PathVariable("language") String language, @RequestParam("user") String userId) {
        try {
            recommendationService.removeSubscription(language, userId);
            return new ResponseEntity<>("Ok", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}