package it.polimi.ingsw.gc27;

import java.util.ArrayList;
import java.util.Stack;

public class ObjectiveDeck extends Deck<ObjectiveCard>{
    @Override
    public Stack<ObjectiveCard> createDeck(){
        return new Stack<>();
    }
}