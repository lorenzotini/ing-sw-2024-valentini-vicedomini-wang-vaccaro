package it.polimi.ingsw.gc27.Card;

import java.io.Serializable;

public abstract class Card implements Serializable {
    private int cardID;
    private FrontFace front;
    private BackFace back;
    public Card(){
    }
    public Card(int cardID, FrontFace given_front, BackFace given_back){
        this.cardID = cardID;
        this.front = given_front;
        this.back = given_back;
    }

    public FrontFace getFront() {
        return front;
    }

    public BackFace getBack() {
        return back;
    }

}
