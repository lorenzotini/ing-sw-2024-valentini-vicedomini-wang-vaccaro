package it.polimi.ingsw.gc27.Game;

import it.polimi.ingsw.gc27.Card.Card;
import it.polimi.ingsw.gc27.Enumerations.PawnColour;

public class Player {
    private String username;
    private Card[] hand;
    private Manuscript manuscript;
    private PawnColour pawnColour;
    private Card secretObjective;

    public Player() {
        hand = new Card[3];
    }



    public PawnColour getPawnColour() {
        return pawnColour;
    }

}
