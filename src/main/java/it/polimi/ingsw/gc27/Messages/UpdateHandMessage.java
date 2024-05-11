package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Model.MiniModel;
import it.polimi.ingsw.gc27.View.View;

public class UpdateHandMessage extends Message{
    //this minimodel's class have player, hand and the string setted,
    //player is the only one that has to receive the message
    public UpdateHandMessage(MiniModel miniModel){
        super(miniModel, "You've drawn a card!");
    }

    @Override
    public void reportUpdate(MiniModel miniModel, View view) {
        miniModel.setHand(this.getMiniModel().getHand());
        miniModel.setPlayer(this.getMiniModel().getPlayer());
        view.showString(this.string);
        view.show(miniModel.getHand());
    }
}
