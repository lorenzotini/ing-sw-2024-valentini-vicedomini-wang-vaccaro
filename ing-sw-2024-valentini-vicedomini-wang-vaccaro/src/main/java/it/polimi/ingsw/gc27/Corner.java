package it.polimi.ingsw.gc27;

import Enumerations.CornerSymbol;

//class Corner is used in every card as intended, except from objective cards where every corner is BLACK and symbol = EMPTY.
public class Corner {
    //private CornerPosition position;
    private boolean hidden;
    private boolean black;
    private CornerSymbol symbol;

    public boolean isHidden(){
        return this.hidden;
    };
    public boolean isBlack(){
        return this.black;
    };
    public CornerSymbol getSymbol() {
        return this.symbol;
    }
}
