package it.polimi.ingsw.gc27;

import java.util.Stack;

public abstract class Deck<T extends Card> {
    private Stack<T> deck;
    public Stack<T> newDeck(){
        return createDeck();
    }

    //FACTORY METHOD
    public abstract Stack<T> createDeck();
}
