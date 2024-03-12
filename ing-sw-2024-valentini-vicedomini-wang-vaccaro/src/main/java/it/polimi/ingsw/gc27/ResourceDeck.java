package it.polimi.ingsw.gc27;

import java.util.ArrayList;
import java.util.Stack;

public class ResourceDeck extends Deck<ResourceCard>{
    @Override
    public Stack<ResourceCard> createDeck(){
        return new Stack<>();
    }
}
