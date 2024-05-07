package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Model.MiniModel;

public class UpdateBoardMessage extends Message{
    public UpdateBoardMessage(MiniModel miniModel){
        super(miniModel,"Points!");
    }
}
