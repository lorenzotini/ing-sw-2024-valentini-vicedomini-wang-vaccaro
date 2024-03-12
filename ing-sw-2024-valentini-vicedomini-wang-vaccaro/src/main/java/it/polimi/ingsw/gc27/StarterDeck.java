package it.polimi.ingsw.gc27;

import java.util.Stack;

public class StarterDeck extends Deck<StarterCard>{
    @Override
    public Stack<StarterCard> createDeck(){
        return new Stack<>();
    }
}
