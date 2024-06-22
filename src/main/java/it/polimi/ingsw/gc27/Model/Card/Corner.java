package it.polimi.ingsw.gc27.Model.Card;

import it.polimi.ingsw.gc27.Model.Enumerations.CornerSymbol;

import java.io.Serializable;

/**
 * The Corner class represents a corner of a card in the game
 * This class is used in every card, except for objective cards where every corner is BLACK and the symbol is EMPTY
 */
public class Corner implements Serializable {
    private boolean hidden;
    private boolean black;
    private CornerSymbol symbol;

    /**
     * constructor
     * @param black indicates if the corner is
     * @param symbol the symbol
     */
    public Corner(boolean black, CornerSymbol symbol) {
        this.hidden = false;
        this.black = black;
        this.symbol = symbol;
    }

    /**
     * Checks if the corner is hidden
     * @return true if the corner is hidden, false otherwise
     */
    public boolean isHidden() {
        return hidden;
    }

    /**
     * Sets the corner to hidden status
     * @param hidden the hidden status to set
     */
    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    /**
     * Checks if the corner is black
     * @return true if the corner is black, false otherwise
     */
    public boolean isBlack() {
        return black;
    }

    /**
     * Gets the symbol of the corner
     * @return the symbol of the corner
     */
    public CornerSymbol getSymbol() {
        return symbol;
    }
}