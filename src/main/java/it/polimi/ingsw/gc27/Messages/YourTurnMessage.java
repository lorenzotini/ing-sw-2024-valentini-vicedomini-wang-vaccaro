package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;
import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.View;

public class YourTurnMessage extends Message{

    public YourTurnMessage(MiniModel miniModel, String string) {
        super(miniModel, string);
    }

    @Override
    public void reportUpdate(VirtualView client, View view) {
        view.showString("It's your turn to play");
    }

}
