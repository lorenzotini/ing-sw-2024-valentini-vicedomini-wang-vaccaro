package it.polimi.ingsw.gc27.Model.Card;

import java.io.Serializable;

public abstract class Card implements Serializable {

    private int cardID;
    private FrontFace front;
    private BackFace back;

    /**
     * constructor
     * @param cardID card's id
     * @param given_front front face
     * @param given_back back face
     */
    public Card(int cardID, FrontFace given_front, BackFace given_back){
        this.cardID = cardID;
        this.front = given_front;
        this.back = given_back;
    }

    /**
     * getters
     */
    public FrontFace getFront() {
        return front;
    }
    public BackFace getBack() {
        return back;
    }


}
