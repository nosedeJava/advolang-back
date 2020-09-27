package advolang.app.exceptions;

/**
 * RecommendationNotFound
 */
public class RecommendationNotFound extends Exception{

    /**
     *
     */
    private static final long serialVersionUID = 4522165196557401637L;

    public RecommendationNotFound(String message){
        super(message);
    }    
}