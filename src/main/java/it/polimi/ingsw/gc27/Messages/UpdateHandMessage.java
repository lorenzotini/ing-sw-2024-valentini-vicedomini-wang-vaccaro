package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Model.MiniModel;

public class UpdateHandMessage extends Message{

    public UpdateHandMessage(MiniModel miniModel){
        super(miniModel, "You've drawn a card!");
    }
}
