package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Model.MiniModel;

import java.io.Serializable;

public abstract class Message implements Serializable {
    private final MiniModel miniModel;
    private final String string;

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

//    protected Message(String string, MiniModel miniModel){
//        this.string = string;
//        this.miniModel = miniModel;
//    }
}
