package it.polimi.ingsw.gc27.Exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(){
        super();
    }

    public UserNotFoundException(String message){
        super(message);
    }
}
