package it.polimi.ingsw.gc27;

import java.util.ArrayList;
import java.util.Stack;

public class GoldDeck extends Deck<GoldCard>{
    @Override
    public Stack<GoldCard> createDeck(){
        return new Stack<>();
    }
}
