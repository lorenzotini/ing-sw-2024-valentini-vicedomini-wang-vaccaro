package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Model.MiniModel;
import it.polimi.ingsw.gc27.View.View;

public class SendStringMessage extends Message{

    public SendStringMessage(String message){
        super(message);
    }

    @Override
    public void reportUpdate(MiniModel miniModel, View view) {
        view.showString(this.string);
    }

}
