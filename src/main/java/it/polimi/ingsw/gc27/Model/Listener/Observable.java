package it.polimi.ingsw.gc27.Model.Listener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public abstract class Observable {
    protected ArrayList<Observer> observers;
    protected boolean isChanged = false;
    void addObserver(Observer o) {
        observers.add(o);
    }
    void deleteObserver(Observer o){
        observers.remove(o);
    }
    boolean hasChanged(){
        return isChanged;
    }
    public void notifyObservers(){
    /*
        for(Observer o : observers){
            o.update(this);
        }
        clearChanged();*/
    }
    void setChanged(){
        this.isChanged = true;
    }
    void clearChanged(){
        this.isChanged = false;
    }
}
