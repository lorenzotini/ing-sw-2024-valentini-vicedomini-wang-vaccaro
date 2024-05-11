package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Model.MiniModel;
import it.polimi.ingsw.gc27.View.View;

public class UpdateBoardMessage extends Message{
    //this minimodel's class have only board and the string setted,
    //every player has to receive the message
    public UpdateBoardMessage(MiniModel miniModel){
        super(miniModel,"Points!");
    }

    @Override
    public void reportUpdate(MiniModel miniModel, View view) {
        miniModel.setBoard(this.getMiniModel().getBoard());
        view.showString(this.string);
        view.show(miniModel.getBoard());
    }
}
