package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;
import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.View;

import java.io.Serializable;

public abstract class Message implements Serializable {

    protected final MiniModel miniModel;
    protected final String string;

    //every extension of this class will have different params of the minimodel set,
    //you can find the specified params set noted in every class

    protected Message(MiniModel miniModel, String string) {
        this.miniModel = miniModel;
        this.string = string;
    }

    protected Message(MiniModel miniModel) {
        this.miniModel = miniModel;
        this.string = null;
    }

    protected Message(String string) {
        this.string = string;
        this.miniModel = null;
    }

    public MiniModel getMiniModel() {
        return miniModel;
    }

    public abstract void reportUpdate(VirtualView client, View view);

    public String getString() {
        return this.string;
    }

}
