package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Model.MiniModel;

public class UpdateMarketMessage extends Message{
    public UpdateMarketMessage(MiniModel miniModel){
        super(miniModel, "The Market has been updated!");
    }
}
