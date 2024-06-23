package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;
import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.View;

public class ContinueGameMessage extends Message{
    public ContinueGameMessage(MiniModel miniModel) {
        super(miniModel);
    }

    @Override
    public void reportUpdate(VirtualView client, View view) {
        view.resumeTheMatch();
    }
}
