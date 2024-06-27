package it.polimi.ingsw.gc27.Model.Card;

import java.io.Serializable;

/**
 * The Card class represents a basic card in the game
 * It implements the Serializable interface to allow for object serialization
 */
public abstract class Card implements Serializable {

    private int cardID;
    private FrontFace front;
    private BackFace back;

    /**
     * constructor for the card
     *
     * @param cardID      card's id
     * @param given_front front face
     * @param given_back  back face
     */
    public Card(int cardID, FrontFace given_front, BackFace given_back) {
        this.cardID = cardID;
        this.front = given_front;
        this.back = given_back;
    }

    /**
     * Gets the front face of the card
     *
     * @return the front face
     */
    public FrontFace getFront() {
        return front;
    }

    /**
     * Gets the back face of the card
     *
     * @return the back face
     */
    public BackFace getBack() {
        return back;
    }


}
