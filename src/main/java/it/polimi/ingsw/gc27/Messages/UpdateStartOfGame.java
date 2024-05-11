package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Model.MiniModel;
import it.polimi.ingsw.gc27.View.View;

public class UpdateStartOfGame extends Message {
    protected UpdateStartOfGame(MiniModel miniModel, String string) {
        super(miniModel, string);
    }

    @Override
    public void reportUpdate(MiniModel miniModel, View view) {
        miniModel.copy(this.getMiniModel());
        view.startTheGame();
    }
}
