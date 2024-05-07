package it.polimi.ingsw.gc27.Listeners.Messages;

import it.polimi.ingsw.gc27.Model.Game.Player;
import it.polimi.ingsw.gc27.Model.MiniModel;

public class NotYourTurnMessage extends Message{
    public NotYourTurnMessage(String errorMessage, MiniModel currentPlayer) {
        super(errorMessage, currentPlayer);
    }
}
