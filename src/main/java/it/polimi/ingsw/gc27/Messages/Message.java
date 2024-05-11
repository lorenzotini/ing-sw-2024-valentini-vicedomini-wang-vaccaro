package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Model.MiniModel;
import it.polimi.ingsw.gc27.View.View;

import java.io.Serializable;

public abstract class Message implements Serializable {
    private final MiniModel miniModel;
    public final String string;
    //every extension of this class will have different params of the minimodel setted,
    //you can find the specified params setted noted in every class

    protected Message(MiniModel miniModel, String string) {
        this.miniModel = miniModel;
        this.string = string;
    }
    protected Message(MiniModel miniModel){
        this.miniModel = miniModel;
        this.string = null;
    }
    protected Message(String string){
        this.string = string;
        this.miniModel = null;
    }
    public MiniModel getMiniModel(){
        return miniModel;
    }

    public abstract void reportUpdate(MiniModel miniModel, View view) ;


//    protected Message(String string, MiniModel miniModel){
//        this.string = string;
//        this.miniModel = miniModel;
//    }
}
