package it.polimi.ingsw.gc27.Exceptions;
// TODO serve questa classe di eccezioni?
public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(){
        super();
    }

    public UserNotFoundException(String message){
        super(message);
    }
}
