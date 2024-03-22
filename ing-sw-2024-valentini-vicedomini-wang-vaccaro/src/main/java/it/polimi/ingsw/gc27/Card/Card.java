package it.polimi.ingsw.gc27.Card;

public abstract class Card {
    private int cardID;
    private FrontFace front;
    private BackFace back;
    public Card(int cardID, FrontFace given_front, BackFace given_back){
        this.cardID = cardID;
        this.front = given_front;
        this.back = given_back;
    }



}
