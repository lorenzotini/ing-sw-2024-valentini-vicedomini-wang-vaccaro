package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Model.MiniModel;
import it.polimi.ingsw.gc27.View.View;

public class UpdateManuscriptMessage extends Message{
    //this minimodel's class have player, manuscript and the string setted,
    //player is the only one that has to receive the message
    public UpdateManuscriptMessage(MiniModel miniModel){
        super(miniModel, "Something changed in your manuscript!");
    }

    @Override
    public void reportUpdate(MiniModel miniModel, View view) {
        miniModel.setPlayer(this.getMiniModel().getPlayer());
        miniModel.setManuscript(this.getMiniModel().getManuscript());
        view.showString(this.string);
        view.show(miniModel.getManuscript());
    }
}
