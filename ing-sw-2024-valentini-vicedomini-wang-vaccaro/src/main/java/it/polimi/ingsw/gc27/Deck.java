package it.polimi.ingsw.gc27;

import java.util.ArrayList;
import java.util.Stack;

public abstract class Deck<T extends Card> {
    private Stack<T> deck;

    public Deck (ArrayList<T> card){
        deck = new Stack<T>();
        for (T x : card ){
            this.add(x);
        }
    }




    public T take(){
        return deck.pop();
    }
    public void add(T c){
        deck.push(c);

    }
    public int size(){
        return deck.size();
    }
}
