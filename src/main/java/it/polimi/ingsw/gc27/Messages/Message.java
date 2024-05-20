package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Model.MiniModel;
import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.View;

import java.io.Serializable;
import java.rmi.RemoteException;

public abstract class Message implements Serializable {
    private final MiniModel miniModel;
    protected final String string;

    //every extension of this class will have different params of the minimodel setted,
    //you can find the specified params setted noted in every class

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

    public abstract void reportUpdate(VirtualView client, View view) throws RemoteException;

    public String takeString() {
        return this.string;
    }


//    protected Message(String string, MiniModel miniModel){
//        this.string = string;
//        this.miniModel = miniModel;
//    }
}
