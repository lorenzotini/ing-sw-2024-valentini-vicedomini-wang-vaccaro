package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Model.MiniModel;

public class NotYourTurnMessage extends Message{
    public NotYourTurnMessage(String errorMessage, MiniModel currentPlayer) {
        super(currentPlayer, errorMessage);
    }
}
