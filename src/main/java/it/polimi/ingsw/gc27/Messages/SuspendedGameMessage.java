package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.View;

public class SuspendedGameMessage extends Message{
    public SuspendedGameMessage(String string){
        super(string);
    }
    @Override
    public void reportUpdate(VirtualView client, View view) {
        view.showString(this.string);
    }
}
