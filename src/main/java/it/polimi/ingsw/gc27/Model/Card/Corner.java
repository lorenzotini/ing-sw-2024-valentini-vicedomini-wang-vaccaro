package it.polimi.ingsw.gc27.Model.Card;

import it.polimi.ingsw.gc27.Model.Enumerations.CornerSymbol;

import java.io.Serializable;

//class Corner is used in every card as intended, except from objective cards where every corner is BLACK and symbol = EMPTY.
public class Corner implements Serializable {
    private boolean hidden;
    private boolean black;
    private CornerSymbol symbol;

    public Corner(boolean black, CornerSymbol symbol) {
        this.hidden = false;
        this.black = black;
        this.symbol = symbol;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean isBlack() {
        return black;
    }

    public CornerSymbol getSymbol() {
        return symbol;
    }
}