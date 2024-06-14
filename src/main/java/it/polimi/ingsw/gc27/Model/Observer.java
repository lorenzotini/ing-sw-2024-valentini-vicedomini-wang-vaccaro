package it.polimi.ingsw.gc27.Model;

import it.polimi.ingsw.gc27.Messages.Message;

public interface Observer  {

    void update(Message message) ;

    String getPlayerUsername();

}
