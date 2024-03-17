package it.polimi.ingsw.gc27;

import it.polimi.ingsw.gc27.Enumerations.PawnColour;

import java.util.ArrayList;

public class Player {
    private String username;
    private Card[] hand;
    private Manuscript manuscript;
    private PawnColour pawnColour;
    private Card secretObjective;

    public Player() {
        hand = new Card[3];
    }

    public void setUsername(String x) {
        this.username = x;
    }
    public void setManuscript(Manuscript x) {
        this.manuscript = x;
    }
    public void setPawnColour(PawnColour x) {
        this.pawnColour = x;
    }

    public String getUsername() {
        return username;
    }
    public Manuscript getManuscript() {
        return manuscript;
    }
    public PawnColour getPawnColour() {
        return pawnColour;
    }
    public Card[] getHand() {
        return hand;
    }

    public void setHand(Card x, Card y, Card z) {
        this.hand[0] = x;
        this.hand[1] = y;
        this.hand[2] = z;
    }
}
