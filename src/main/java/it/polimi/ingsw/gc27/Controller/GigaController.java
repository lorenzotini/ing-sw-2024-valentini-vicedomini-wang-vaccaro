package it.polimi.ingsw.gc27.Controller;

import it.polimi.ingsw.gc27.Exceptions.UserNotFoundException;

import java.util.List;
import java.util.ArrayList;

public class GigaController {

    private List<GameController> gameControllers = new ArrayList<>();

    public GameController userToGameController(String username){
        for(var c : gameControllers){
            if(c.getGame().getPlayers().stream().anyMatch(p -> p.getUsername().equals(username))){
                return c;
            }
        }
        throw new UserNotFoundException(username + "does not exist");
    }

}
