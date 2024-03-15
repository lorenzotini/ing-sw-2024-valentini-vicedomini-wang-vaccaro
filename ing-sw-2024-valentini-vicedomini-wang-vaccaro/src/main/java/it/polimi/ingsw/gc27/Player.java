package it.polimi.ingsw.gc27;

import it.polimi.ingsw.gc27.Enumerations.PawnColour;

import java.util.ArrayList;

public class Player {
    private String username;
    private ArrayList<Card> hand;
    private Manuscript manuscript;
    private PawnColour pawnColour;

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
    public ArrayList<Card> getHand() {
        return hand;
    }

    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }
}
