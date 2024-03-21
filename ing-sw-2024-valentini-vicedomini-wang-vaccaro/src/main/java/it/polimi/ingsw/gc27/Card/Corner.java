package it.polimi.ingsw.gc27.Card;

import it.polimi.ingsw.gc27.Enumerations.CornerSymbol;

//class Corner is used in every card as intended, except from objective cards where every corner is BLACK and symbol = EMPTY.
public class Corner {
    private boolean hidden;
    private boolean black;
    private CornerSymbol symbol;

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean isBlack() {
        return black;
    }

    public void setBlack(boolean black) {
        this.black = black;
    }

    public CornerSymbol getSymbol() {
        return symbol;
    }

    public void setSymbol(CornerSymbol symbol) {
        this.symbol = symbol;
    }



    /*public boolean isHidden(){
        return this.hidden;
    };
    public void setHidden(boolean bool){

        this.hidden = bool;
    }
    public boolean isBlack(){

        return this.black;
    };
    public CornerSymbol getSymbol() {

        return this.symbol;
    }*/
}