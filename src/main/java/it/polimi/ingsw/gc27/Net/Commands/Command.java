package it.polimi.ingsw.gc27.Net.Commands;


import it.polimi.ingsw.gc27.Controller.GameController;

import java.io.IOException;
import java.io.Serializable;

public interface Command extends Serializable {

    void execute(GameController gc) ;
    String getPlayerName();
}
