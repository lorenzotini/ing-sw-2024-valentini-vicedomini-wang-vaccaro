package it.polimi.ingsw.gc27.Card;

public abstract class Card {
    private int cardID;
    private FrontFace front;
    private BackFace back;
    public Card(FrontFace given_front, BackFace given_back){
        this.front = given_front;
        this.back = given_back;
    }

    public int getId() {
        return cardID;
    }

    public FrontFace getFrontFace() {
        return front;
    }

    public BackFace getBackFace() {
        return back;
    }
}
