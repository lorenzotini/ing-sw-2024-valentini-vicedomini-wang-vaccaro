package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Model.MiniModel;
import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.View;

public class GenericErrorMessage extends Message{
    //this minimodel's class have only currentPlayer and the string set,
    //currentPlayer is the player that has to receive the message
    public GenericErrorMessage(String errorMessage, MiniModel currentPlayer) {
        super(currentPlayer, errorMessage);
    }

    @Override
    public void reportUpdate(VirtualView client, View view) {
        view.showString(this.string);
        view.koAck("invalid action: " + this.string);
    }

}
