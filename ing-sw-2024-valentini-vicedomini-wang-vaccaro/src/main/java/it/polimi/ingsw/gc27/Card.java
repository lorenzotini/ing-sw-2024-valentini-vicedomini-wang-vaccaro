package it.polimi.ingsw.gc27;

import java.util.ArrayList;

public abstract class Card {
    private int id;
    //private ArrayList<Symbol> permanentResources;
    private FrontFace frontFace;
    private BackFace backFace;
    public Card(FrontFace front, BackFace back){
        this.frontFace = front;
        this.backFace = back;
    }

    public int getId() {
        return id;
    }

    public FrontFace getFrontFace() {
        return frontFace;
    }

    public BackFace getBackFace() {
        return backFace;
    }
}
