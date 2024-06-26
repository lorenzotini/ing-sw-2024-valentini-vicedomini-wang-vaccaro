package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.View;

public class PingMessage extends Message{

    public PingMessage() {
        super("forza napoli");
    }

    @Override
    public void reportUpdate(VirtualView client, View view) {

    }
}
