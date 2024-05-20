package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.View;

public class PlayerJoinedMessage extends Message {
    //this minimodel's class have nothing setted,
    //every player has to receive the message
    public PlayerJoinedMessage(String username) {
        super("\n" + username + " joined the game");
    }

    @Override
    public void reportUpdate(VirtualView client, View view) {
        view.showString(this.string);
    }
}
