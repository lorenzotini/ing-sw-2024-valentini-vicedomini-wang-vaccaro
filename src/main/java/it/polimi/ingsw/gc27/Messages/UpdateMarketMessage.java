package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Model.MiniModel;
import it.polimi.ingsw.gc27.View.View;

public class UpdateMarketMessage extends Message{

    //the minimodel of this class will have only market setted
    //everyone will receive this
    public UpdateMarketMessage(MiniModel miniModel){
        super(miniModel, "The Market has been updated!");
    }

    @Override
    public void reportUpdate(MiniModel miniModel, View view) {
        miniModel.setMarket(this.getMiniModel().getMarket());
        view.showString(this.string);
        view.show(miniModel.getMarket());
    }
}
