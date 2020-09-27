package advolang.app.exceptions;

/**
 * UserBadRequest
 */
public class UserBadRequest extends Exception{

    /**
     *
     */
    private static final long serialVersionUID = -6080714749188290391L;
    
    public UserBadRequest(String message){
        super(message);
    }
}
